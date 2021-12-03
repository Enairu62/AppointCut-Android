package online.appointcut.models

import com.squareup.moshi.Json

class Shop(
    @Json(name = "ShopID")
    val id: Int,
    @Json(name = "ShopName")
    val name: String,
    @Json(name = "Address")
    val address: String,
    @Json(name = "ShopContact")
    val contact: String,
    @Json(name = "Email")
    val email: String,
    @Json(name = "Image")
    val imgSrcUrl: String?,
    @Json(name = "Rating")
    val rating: Float,
    @Json(name = "Longitude")
    val long: Double?,
    @Json(name = "Latitude")
    val lat: Double?
    )