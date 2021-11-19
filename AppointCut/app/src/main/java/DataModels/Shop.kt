package DataModels

import com.squareup.moshi.Json

class Shop(
    @Json(name = "ShopID")
    val id: Int,
    @Json(name = "OwnerID")
    val ownerId: Int,
    @Json(name = "ShopName")
    val name: String,
    @Json(name = "Address")
    val address: String,
    @Json(name = "Contact")
    val contact: String,
    @Json(name = "Email")
    val email: String,
    @Json(name = "Image")
    val imgSrcUrl: String,
    @Json(name = "Rating")
    val rating: Float
    )