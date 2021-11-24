package com.example.appointcut.models

import com.squareup.moshi.Json

data class Barber(
    @Json(name = "EmployeeID") val id: Int,
    @Json(name = "ShopID") val shopId: Int,
    @Json(name = "FullName") val fullName: String
    )