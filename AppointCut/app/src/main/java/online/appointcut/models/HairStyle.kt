package online.appointcut.models

import com.squareup.moshi.Json

data class HairStyle(@Json(name = "name") var name: String) {
    @Json(name = "id") var id: Int = -1
    @Json(name = "image_link") var imageLink: String = ""
    @Json(name = "text_link") var textLink: String = ""
    var description: String = ""
}