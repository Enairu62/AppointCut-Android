package online.appointcut.models

import com.squareup.moshi.Json


/**
 * Schedule of the barber for a single day of the week
 * @param dayOfWeek The day of the week that this is for
 * @param timeIn Starting time of this schedule
 * @param timeOut End time of this schedule
 */
data class Schedule(
    @Json(name = "Date") val dayOfWeek: String
    , @Json(name = "TimeIn") val timeIn: String?
    , @Json(name = "TimeOut") val timeOut:String?) {
    companion object{
        const val SUNDAY = "Sunday"
        const val MONDAY = "Monday"
        const val TUESDAY = "Tuesday"
        const val WEDNESDAY = "Wednesday"
        const val THURSDAY = "Thursday"
        const val FRIDAY = "Friday"
        const val SATURDAY = "Saturday"
    }
}