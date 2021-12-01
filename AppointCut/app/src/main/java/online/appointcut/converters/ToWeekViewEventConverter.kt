package online.appointcut.converters

import android.graphics.Color
import android.util.Log
import com.alamkanak.weekview.WeekViewEvent
import online.appointcut.models.Appointment
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
        val endMinute = endTimeSplit[1].toInt()-1

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
}