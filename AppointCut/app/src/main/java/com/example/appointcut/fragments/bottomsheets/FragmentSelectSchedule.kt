package com.example.appointcut.fragments.bottomsheets

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEvent
import com.example.appointcut.R
import com.example.appointcut.converters.AppointmentToWeekViewEventConverter
import com.example.appointcut.models.Barber
import com.example.appointcut.network.ApcService
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fragments.BottomSheetFragmentAppointmentDetails
import fragments.BottomSheetFragmentSelectBarber
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
        val bottomSheetFragmentAppointmentDetails: BottomSheetDialogFragment =
            BottomSheetFragmentAppointmentDetails()
        val weekView: WeekView = view.findViewById(R.id.weekView)

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
        btnNext.setOnClickListener {
            dismiss()
            bottomSheetFragmentAppointmentDetails.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragmentAppointmentDetails.tag
            )
        }


        weekView.apply {
            val arm = AppointmentRequestManager(viewLifecycleOwner,weekView)
            setMonthChangeListener { newYear, newMonth ->
                arm.requestAppointmentDetails(barber,newYear,newMonth)
            }

            setOnEventClickListener { event, eventRect ->  }

            setEmptyViewClickListener {
                Log.d("FragmentSelectSchedule", "${it.get(Calendar.DATE)}, ${it.time}")
                val timePickerListener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->

                }
                val roundedMinute = 15*(Math.round(it.get(Calendar.MINUTE).toDouble()/15))
                TimePickerDialog(requireContext(),timePickerListener
                ,it.get(Calendar.HOUR),roundedMinute.toInt(),false)
                    .show()
            }

        }

        return view
    }

    private class AppointmentRequestManager(private val lifecycleOwner: LifecycleOwner,
        private val weekView: WeekView){
        private val requests = mutableMapOf<String,List<WeekViewEvent>>()

        fun requestAppointmentDetails(barber: Barber,year: Int,month: Int)
        : List<WeekViewEvent>{
            //if key exists, return contents
            if (requests.containsKey("${barber.id}${year}${month}")) {
                return requests["${barber.id}${year}${month}"]!!
            }
            //otherwise, add to map then get
            else{
                requests["${barber.id}${year}${month}"] = listOf()
                lifecycleOwner.lifecycleScope.launch {
                    requests["${barber.id}${year}${month}"] =
                        getBarberAppointmentAsWeekViewEvent(barber, year, month)
                    weekView.notifyDatasetChanged()
                }
            }
            return requests["${barber.id}${year}${month}"]!!
        }

        private suspend fun getBarberAppointmentAsWeekViewEvent(barber: Barber
                                                                , year: Int, month: Int)
                :List<WeekViewEvent> {
            val weekViewEvent = mutableListOf<WeekViewEvent>()
            Log.d("FragmentSelectSchedule", "get barber apts called")
            //get the appointments
            val appointments = ApcService.retrofitService
                .getBarberScheduleForMonthYear(
                    barber.id, month, year
                )
            //convert the appointments
            for (apt in appointments) {
                val event = AppointmentToWeekViewEventConverter
                    .convertToBasic(apt, 0, "")
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