package com.example.appointcut.models

import com.example.appointcut.network.ApcService
import com.squareup.moshi.Json
import java.util.*

class Barber(
    @Json(name = "EmployeeID") val id: Int,
    @Json(name = "ShopID") val shopId: Int,
    @Json(name = "FullName") val fullName: String
    ){
    private var _schedule: MutableList<Schedule>? = null
    val schedule: List<Schedule>
        get(){
            return _schedule?: throw UninitializedPropertyAccessException(
                "Property has not yet been initialized run fillSchedule first")
        }

    suspend fun fillSchedule(){
        _schedule = ApcService.retrofitService.getBarberSchedule(id)
    }

    /**
     * Returns the Barber's schedule for a given day
     * @param dayOfWeek Day of the week to be fetched
     * @returns Schedule for given day of week, null if none
     * @see Schedule
     */
    fun getDaySchedule(dayOfWeek: String): Schedule?{
        for(s in schedule){
            if (s.dayOfWeek == dayOfWeek) return s
        }
        return null
    }

    /**
     * Returns the Barber's schedule for a given day
     * @param dayOfWeek Day of the week to be fetched
     * @returns Schedule for given day of week, null if none
     * @see Calendar it has static variables
     */
    fun getDaySchedule(dayOfWeek: Int): Schedule?{
        var dayOfWeekString: String = when (dayOfWeek) {
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> ""
        }

        for(s in schedule){
            if (s.dayOfWeek == dayOfWeekString) return s
        }
        return null
    }
}