package com.example.appointcut.fragments.bottomsheets

import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEvent
import com.example.appointcut.R
import com.example.appointcut.converters.ToWeekViewEventConverter
import com.example.appointcut.models.Barber
import com.example.appointcut.network.ApcService
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fragments.BottomSheetFragmentAppointmentDetails
import fragments.BottomSheetFragmentSelectBarber
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*


class FragmentSelectSchedule(private val barber: Barber) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =
            inflater.inflate(R.layout.fragment_bottom_sheet_select_schedule, container, false)
        val btnBack = view.findViewById<View>(R.id.btnBack) as Button
        val btnNext = view.findViewById<View>(R.id.btnNext) as Button
        val bottomSheetFragmentSelectBarber: BottomSheetDialogFragment =
            BottomSheetFragmentSelectBarber()
        val weekView: WeekView = view.findViewById(R.id.weekView)

        //change the behavior of this dialog
        (this.dialog as BottomSheetDialog).behavior.apply {
            isHideable = false
            peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
            isDraggable = false
        }

        btnBack.setOnClickListener {
            dismiss()
            bottomSheetFragmentSelectBarber.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragmentSelectBarber.tag
            )
        }
        btnNext.setOnClickListener { nextFragment() }

        //configure the weekView
        val arm = WeekViewRequestManager(viewLifecycleOwner,weekView)
        weekView.apply {

            setMonthChangeListener { newYear, newMonth ->
                Log.d("SelectSchedule", "Month Change Triggered for $newYear, $newMonth")
                //request for the barbers time table
                arm.requestBarberTimeTable(barber,newYear,newMonth)
            }

            //customer can't pick events
            setOnEventClickListener { _, _ ->
                Toast.makeText(requireContext(),"Time slot is unavailable", Toast.LENGTH_LONG)
                    .show()
            }

            //customer selects a free slot
            emptyViewClickListener = selectTimeSlot
            goToHour(12.0)

        }

        return view
    }

    //Listener for when the customer selects an empty slot
    private val selectTimeSlot = WeekView.EmptyViewClickListener {
        //TODO: Implement checking for appointment conflicts
        Log.d("FragmentSelectSchedule", "${it.get(Calendar.DATE)}, ${it.time}")
        val timePickerListener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
            Toast.makeText(requireContext(),"Selected $i:$i2 time Slot", Toast.LENGTH_LONG)
                .show()
            nextFragment()
        }
        //round to 15 minute increments
        var roundedMinute = 15*(Math.round(it.get(Calendar.MINUTE).toDouble()/15))
        var hour = it.get(Calendar.HOUR_OF_DAY)
        //TimePickerDialog does not like minute of 60
        if (roundedMinute == 60.toLong()){
            roundedMinute = 0
            hour++
        }
        TimePickerDialog(requireContext(),timePickerListener
            ,hour,roundedMinute.toInt(),false)
            .show()

    }

    private fun nextFragment(){
        val bottomSheetFragmentAppointmentDetails = BottomSheetFragmentAppointmentDetails()

        dismiss()
        bottomSheetFragmentAppointmentDetails.show(
            requireActivity().supportFragmentManager,
            bottomSheetFragmentAppointmentDetails.tag
        )
    }

    /**
     * Manages the requests made by the Week View
     */
    private class WeekViewRequestManager(private val lifecycleOwner: LifecycleOwner,
                                         private val weekView: WeekView){
        private val requests = mutableMapOf<String,MutableList<WeekViewEvent>>()

        fun requestBarberTimeTable(barber: Barber, year: Int, month: Int)
        : List<WeekViewEvent>{
            //if key exists, return contents
            if (requests.containsKey("${barber.id}${year}${month}")) {
                return requests["${barber.id}${year}${month}"]!!
            }
            //otherwise, add to map then get from server
            else{
                requests["${barber.id}${year}${month}"] = mutableListOf()
                lifecycleOwner.lifecycleScope.launch {
                    //get and add events
                    requests["${barber.id}${year}${month}"]!!.addAll(
                        getBarberAppointmentAsWeekViewEvent(barber, year, month)
                    )
                    //add off time
                    requests["${barber.id}${year}${month}"]!!.addAll(
                        getBarberOffEvents(barber, year, month)
                    )
                    Log.d("FragmentSelectSchedule","retrieved events for ${barber.id}-${year}-${month}")
                    weekView.notifyDatasetChanged()
                }
            }
            return requests["${barber.id}${year}${month}"]!!
        }

        private suspend fun getBarberOffEvents(barber: Barber, year: Int, month: Int):
                List<WeekViewEvent> {
            //populate barber schedule
            barber.fillSchedule()
            //initialize list to be returned
            val offEvents = mutableListOf<WeekViewEvent>()
            //make the calendar
            val calendar = GregorianCalendar(year, month, 1)
            //while still in wanted month
            while (calendar.get(Calendar.MONTH) == month){
                //get the schedule for this day of week
                barber.getDaySchedule(calendar.get(Calendar.DAY_OF_WEEK))?.also {
                    //separate the times
                    val timeIn = it.timeIn.split(':')
                    val timeOut = it.timeOut.split(':')

                    //before shift
                    val headStart = GregorianCalendar(year,
                        month, calendar.get(Calendar.DAY_OF_MONTH),0,0)
                    val headEnd = GregorianCalendar(year,
                        month, calendar.get(Calendar.DAY_OF_MONTH),
                        timeIn[0].toInt(),timeIn[1].toInt())

                    //after shift
                    val footerStart = GregorianCalendar(year,
                        month, calendar.get(Calendar.DAY_OF_MONTH),
                        timeOut[0].toInt(),timeOut[1].toInt())
                    val footerEnd = GregorianCalendar(year,
                        month, calendar.get(Calendar.DAY_OF_MONTH),
                        24,0)

                    //add the events
                    offEvents.add(
                        WeekViewEvent(0,"",headStart,headEnd).also{
                            it.color = Color.BLACK
                        }
                    )
                    offEvents.add(
                        WeekViewEvent(0,"",footerStart,footerEnd).also{
                            it.color = Color.BLACK
                        }
                    )
                }
                //increment by one day
                calendar.add(Calendar.DATE, 1)
            }

            return offEvents
        }

        private suspend fun getBarberAppointmentAsWeekViewEvent(barber: Barber
                                                                , year: Int, month: Int)
                :MutableList<WeekViewEvent> {
            val weekViewEvent = mutableListOf<WeekViewEvent>()
            Log.d("FragmentSelectSchedule", "get barber apts called")
            //get the appointments
            val appointments = ApcService.retrofitService
                .getBarberAppointmentsForMonthYear(
                    barber.id, month, year
                )
            //convert the appointments
            for (apt in appointments) {
                val event = ToWeekViewEventConverter
                    .fromBasicAppointment(apt, 0, "")
                Log.d("SelectSchedule", "${event.startTime.time}")
                weekViewEvent.add(
                    event
                )
            }
            Log.d("FragmentSelectSchedule", "${barber.id}, ${month}, ${year}")
            return weekViewEvent
        }
    }


}