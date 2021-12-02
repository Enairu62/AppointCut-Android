package online.appointcut.models

import java.util.*

data class WeekViewEvent(
    val id: Long,
    val title: String,
    val startTime: Calendar,
    val endTime: Calendar
){
    var color: Int? = null
}