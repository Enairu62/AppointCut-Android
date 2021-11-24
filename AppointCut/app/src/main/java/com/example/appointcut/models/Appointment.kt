package com.example.appointcut.models

import com.squareup.moshi.Json
import java.util.*

class Appointment(
    @Json(name = "Date")
    private val _rawDate: String,
    @Json(name = "TimeIn")
    val timeIn: String,
    @Json(name = "TimeOut")
    val timeOut: String
) {
    public val date: String
        get(){
            return _rawDate.substringBefore("T")
        }
}