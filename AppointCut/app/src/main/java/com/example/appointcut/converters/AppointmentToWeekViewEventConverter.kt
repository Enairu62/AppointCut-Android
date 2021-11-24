package com.example.appointcut.converters

import android.graphics.Color
import android.util.Log
import com.alamkanak.weekview.WeekViewEvent
import com.example.appointcut.models.Appointment
import java.util.*

object AppointmentToWeekViewEventConverter {
    fun convertToBasic(appointment: Appointment, eventId: Int, eventName: String): WeekViewEvent{
        //split the values
        val dateSplit = appointment.date.split('-')
        val startTimeSplit = appointment.timeIn.split(':')
        val endTimeSplit = appointment.timeOut.split(':')
        //Date
        val year = dateSplit[0].toInt()
        val month = dateSplit[1].toInt()
        val day = dateSplit[2].toInt()
        //Start Time
        val startHour = startTimeSplit[0].toInt()
        val startMinute = startTimeSplit[1].toInt()
        //End Time
        val endHour = endTimeSplit[0].toInt()
        val endMinute = endTimeSplit[1].toInt()

        //create the calendar objects
        val startTime = GregorianCalendar().apply{
            set(year,month-1,day+1,startHour,startMinute)
        }
        val endTime = GregorianCalendar().apply {
            set(year,month-1,day+1,endHour,endMinute)
        }
        Log.d("Converter","${startTime.time}")

        return WeekViewEvent(eventId.toLong(),eventName,startTime,endTime).apply { color = Color.RED }
    }
}