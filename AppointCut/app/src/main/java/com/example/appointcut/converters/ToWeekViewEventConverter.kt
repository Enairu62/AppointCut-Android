package com.example.appointcut.converters

import android.graphics.Color
import android.util.Log
import com.alamkanak.weekview.WeekViewEvent
import com.example.appointcut.models.Appointment
import com.example.appointcut.models.Schedule
import java.util.*

object ToWeekViewEventConverter {
    fun fromBasicAppointment(appointment: Appointment, eventId: Int, eventName: String)
    : WeekViewEvent{
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
            set(year,month-1,day,startHour,startMinute)
        }
        val endTime = GregorianCalendar().apply {
            set(year,month-1,day,endHour,endMinute)
        }
        Log.d("Converter","${startTime.time}")


        val event =  WeekViewEvent(eventId.toLong(),eventName,startTime,endTime)
        event.color = Color.RED
        return  event
    }

    /**
     * Converts given schedule into a WeekViewEvent
     * @param schedule Schedule to be converted
     * @param eventId Id of the resulting event
     * @param eventName Name of the resulting event
     * @returns WeekViewEvent of a given Schedule
     */
//    fun convertFromSchedule(schedule: Schedule, eventId: Int, eventName: String)
//    :List<WeekViewEvent>{
//        val calendar = GregorianCalendar()
//        //get the header event
//        //00:00 til timein
//
//        //get the footer event
//        //time out til 2400
//    }
}