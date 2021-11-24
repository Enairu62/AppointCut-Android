package com.example.appointcut.models

import com.squareup.moshi.Json

data class ShopService(
    @Json(name = "ID") val id: Int,
    @Json(name = "shopID") val shopId: Int,
    @Json(name = "Service") val name: String,
    @Json(name = "Price") val price: Int,
    @Json(name= "Duration") val duration: Float,
) {
    @Transient var imgResource: Int = 0
}