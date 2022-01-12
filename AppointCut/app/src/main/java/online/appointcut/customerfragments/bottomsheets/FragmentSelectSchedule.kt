package online.appointcut.customerfragments.bottomsheets

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.alamkanak.weekview.WeekView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fragments.BottomSheetFragmentAppointmentDetails
import fragments.BottomSheetFragmentSelectBarber
import kotlinx.coroutines.launch
import online.appointcut.R
import online.appointcut.adapters.ScheduleWeekViewAdapter
import online.appointcut.converters.ToWeekViewEventConverter
import online.appointcut.models.Appointment
import online.appointcut.models.Barber
import online.appointcut.models.DateTime
import online.appointcut.models.WeekViewEvent
import online.appointcut.network.ApcService
import java.util.*

@SuppressLint("SetTextI18n")
class FragmentSelectSchedule(private val barber: Barber) : BottomSheetDialogFragment() {
    private val sharedViewModel: Appointment by activityViewModels()
    private lateinit var weekViewAdapter: ScheduleWeekViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =
            inflater.inflate(R.layout.fragment_bottom_sheet_select_schedule, container, false)
        val btnBack = view.findViewById<View>(R.id.btnBack) as Button
        val bottomSheetFragmentSelectBarber: BottomSheetDialogFragment =
            BottomSheetFragmentSelectBarber()
        val estimate = view.findViewById<TextView>(R.id.estimate)

        //change the behavior of this dialog
        (this.dialog as BottomSheetDialog).behavior.apply {
            isHideable = false
            peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
            isDraggable = false
        }
        estimate.text = "${sharedViewModel.serviceDuration} minutes"

        btnBack.setOnClickListener {
            dismiss()
            bottomSheetFragmentSelectBarber.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragmentSelectBarber.tag
            )
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weekView: WeekView = view.findViewById(R.id.weekView)
        weekView.scrollToTime(12,0)

        //configure the weekView
        weekViewAdapter = ScheduleWeekViewAdapter(::loadMoreEvent,::onEmptyClick)
        weekView.adapter = weekViewAdapter
    }


    private fun loadMoreEvent(startDate: Calendar, endDate: Calendar) {
        val eventsList = mutableListOf<WeekViewEvent>()
        Log.d(
            "SelectSchedule", "Month Change Triggered for ${startDate.get(Calendar.MONTH)}, ${
                endDate.get(
                    Calendar.MONTH
                )
            }"
        )

        lifecycleScope.launch {
            //initial input
            //off events
            Log.d("SelectSchedule","Off\n")
            eventsList.addAll(
                barberOffEvents(
                    barber,
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH)
                )
            )
            //appointments
            Log.d("SelectSchedule","Apt\n")
            eventsList.addAll(
                barberAppointments(
                    barber,
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH)
                )
            )
            //increment start month until == end month
            while (startDate.get(Calendar.MONTH) != endDate.get(Calendar.MONTH)) {
                startDate.add(Calendar.MONTH, 1)
                //off events
                Log.d("SelectSchedule","Off\n")
                eventsList.addAll(
                    barberOffEvents(
                        barber,
                        startDate.get(Calendar.YEAR),
                        startDate.get(Calendar.MONTH)
                    )
                )
                //appointments
                Log.d("SelectSchedule","Apt\n")
                eventsList.addAll(
                    barberAppointments(
                        barber,
                        startDate.get(Calendar.YEAR),
                        startDate.get(Calendar.MONTH)
                    )
                )
            }
            weekViewAdapter.submitList(eventsList)
        }
    }

    private suspend fun barberOffEvents(barber: Barber, year:Int, month: Int): List<WeekViewEvent>{
        val offEvents = mutableListOf<WeekViewEvent>()
        //fill barber schedule
        barber.fillSchedule()

        val calendarCounter = GregorianCalendar(year,month,1)
        while(calendarCounter.get(Calendar.MONTH) == month){
            //get the day of week
            val dayOfWeek = calendarCounter.get(Calendar.DAY_OF_WEEK)
            //get corresponding barber schedule
            val barberSchedule = barber.getDaySchedule(dayOfWeek)

            //format to events
            //make header event
            val headStart = GregorianCalendar(
                year,
                month,
                calendarCounter.get(Calendar.DATE),
                0,
                0
            )
            val headEnd = GregorianCalendar(
                year,
                month,
                calendarCounter.get(Calendar.DATE),
                barberSchedule?.timeIn?.split(":")?.get(0)?.toInt() ?:0,
                barberSchedule?.timeIn?.split(":")?.get(1)?.toInt() ?:0
            )
            val headerEvent = WeekViewEvent(0,"",headStart,headEnd)

            //make footer event
            val footStart = GregorianCalendar(
                year,
                month,
                calendarCounter.get(Calendar.DATE),
                barberSchedule?.timeOut?.split(":")?.get(0)?.toInt() ?:0,
                barberSchedule?.timeOut?.split(":")?.get(1)?.toInt() ?:0
            )
            val footEnd = GregorianCalendar(
                year,
                month,
                calendarCounter.get(Calendar.DATE),
                23,
                59,
                59
            )
            val footerEvent = WeekViewEvent(0,"",footStart,footEnd)

            //add event
            offEvents.add(headerEvent)
            offEvents.add(footerEvent)

            //increment calendar counter
            calendarCounter.add(Calendar.DATE,1)
        }

        return offEvents
    }

    private suspend fun barberAppointments(barber: Barber, year: Int, month: Int): List<WeekViewEvent> {
        Log.d("barberAppt","$year - $month")
        val appointments = ApcService.retrofitService.getBarberAppointmentsForMonthYear(
            barber.id,
            month+1,
            year
        )

        val aptEventList = mutableListOf<WeekViewEvent>()
        for(apt in appointments){
            aptEventList.add(
                ToWeekViewEventConverter.fromBasicAppointment(
                    apt,
                    0,""
                )
            )
        }
        return aptEventList
    }

    //Listener for when the customer selects an empty slot
    private fun onEmptyClick(selectedDate: Calendar) {
        Log.d("FragmentSelectSchedule", "${selectedDate.get(Calendar.DATE)}, ${selectedDate.time}")

        //round to 15 minute increments
        var roundedMinute = 15 * (Math.floor(selectedDate.get(Calendar.MINUTE).toDouble() / 15))
        var hour = selectedDate.get(Calendar.HOUR_OF_DAY)
        //Correction for minute of 60
        if (roundedMinute == 60.toDouble()) {
            roundedMinute = 0.toDouble()
            hour++
        }

        //give time to the calendar with the date
        selectedDate.apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, roundedMinute.toInt())
        }
        //set time in and out
        var minute = selectedDate.get(Calendar.MINUTE).toString()
        //add a zero if single digit
        if (minute.length == 1) minute = "0$minute"
        //store to sharedViewModel
        sharedViewModel.date =
            "${selectedDate.get(Calendar.YEAR)}-${selectedDate.get(Calendar.MONTH) + 1}-${selectedDate.get(Calendar.DATE)}"
        sharedViewModel.timeIn = "${selectedDate.get(Calendar.HOUR_OF_DAY)}:${minute}"
        selectedDate.add(Calendar.MINUTE, sharedViewModel.serviceDuration)
        //add the duration to the time
        //add a zero if single digit
        minute = selectedDate.get(Calendar.MINUTE).toString()
        if (minute.length == 1) minute = "0$minute"
        sharedViewModel.timeOut = "${selectedDate.get(Calendar.HOUR_OF_DAY)}:${minute}"

        Log.d(
            "FragmentSelectSchedule", "${sharedViewModel.date} | " +
                    "${sharedViewModel.timeIn} | " +
                    "${sharedViewModel.timeOut}"
        )
        //check for conflict
        var conflicts = 0
        //Employee schedule conflict
        //get employee schedule for the day
        val date = GregorianCalendar(
            sharedViewModel.date.split("-")[0].toInt(),
            sharedViewModel.date.split("-")[1].toInt()-1,
            sharedViewModel.date.split("-")[2].toInt()
        )
        val empSchedule = barber.getDaySchedule(date.get(Calendar.DAY_OF_WEEK))
        //if null, barber is day off
        if (empSchedule?.timeIn == null) conflicts++
        else {
            //check if appointment timeout > schedule timeout
            val aptEndTime =
                "${sharedViewModel.timeOut.split(":")[0]}${sharedViewModel.timeOut.split(":")[1]}".toInt()
            val schedEndTime = "${empSchedule?.timeOut?.split(":")?.get(0)}${
                empSchedule?.timeOut?.split(":")
                    ?.get(1)
            }".toInt()
            if (aptEndTime > schedEndTime) conflicts++

            //check if appt timein < schedule timeint
            val aptStartTime =
                "${sharedViewModel.timeIn.split(":")[0]}${sharedViewModel.timeIn.split(":")[1]}".toInt()
            val schedStartTime = "${empSchedule?.timeIn?.split(":")?.get(0)}${
                empSchedule?.timeIn?.split(":")?.get(1)
            }".toInt()
            if (aptStartTime < schedStartTime) conflicts++
        }


        //appointments conflict
        lifecycleScope.launch{
            //check if selected date is 1 day in advance
            //get server time
            val today = ApcService.retrofitService.getDateTime()
            val selected = ("${selectedDate.get(Calendar.YEAR)}" +
                    (selectedDate.get(Calendar.MONTH)+1).toString().padStart(2,'0') +
                    selectedDate.get(Calendar.DATE).toString().padStart(2,'0')).toInt()
            var isADayBefore = true
            //compare with selected date
            if("${today.year}${today.month}${today.day}".toInt() >= selected){
                isADayBefore=false
            }
            //set isADayBefore


            Log.d("FSSchedule", "Today Date:" + "${today.year}${today.month}${today.day}".toInt())
            Log.d("FSSchedule", "chosenDate Date: $selected")
            conflicts += ApcService.retrofitService.checkConflict(
                sharedViewModel.employeeId,"${sharedViewModel.date}",
                sharedViewModel.timeIn,
                sharedViewModel.timeOut
            )

            if(!isADayBefore){
                Toast.makeText(requireContext(),"Appointments must be a day prior", Toast.LENGTH_SHORT)
                    .show()
            }else if (conflicts == 0 ) nextFragment()
            else
                Toast.makeText(requireContext(),"Conflicting appointment found", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    private fun nextFragment() {
        val bottomSheetFragmentAppointmentDetails = BottomSheetFragmentAppointmentDetails()

        dismiss()
        //it works so don't question it
        FragmentAppointmentDetails().show(
            requireActivity().supportFragmentManager,
            bottomSheetFragmentAppointmentDetails.tag
        )
    }
}