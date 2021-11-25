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
    val date: String
        get(){
            return _rawDate.substringBefore("T")
        }

    @Json(name = "CustomerID") var customerID = 0
    @Json(name = "ShopID") var shopId = 0
    @Json(name = "EmployeeID") var employeeId = 0
    @Json(name = "ShopServicesID") var shopServiceId = 0
    @Json(name = "HaircutID") var haircutId = 0
    var amountDue = 0.toDouble()
    @Json(name = "appStatusID")var appStatusId = 0

}