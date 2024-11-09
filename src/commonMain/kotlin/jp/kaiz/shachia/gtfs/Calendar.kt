package jp.kaiz.shachia.gtfs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Calendar(
    @SerialName("service_id")
    val id: String,

    @SerialName("monday")
    val monday: Int,

    @SerialName("tuesday")
    val tuesday: Int,

    @SerialName("wednesday")
    val wednesday: Int,

    @SerialName("thursday")
    val thursday: Int,

    @SerialName("friday")
    val friday: Int,

    @SerialName("saturday")
    val saturday: Int,

    @SerialName("sunday")
    val sunday: Int,

    @SerialName("start_date")
    val startDate: String,

    @SerialName("end_date")
    val endDate: String
)

fun calendar(init: CalendarBuilder.() -> Unit) = CalendarBuilder().apply(init).build()

class CalendarBuilder {
    var id: String = ""
    var monday: Int = 0
    var tuesday: Int = 0
    var wednesday: Int = 0
    var thursday: Int = 0
    var friday: Int = 0
    var saturday: Int = 0
    var sunday: Int = 0
    var startDate: String = ""
    var endDate: String = ""

    fun build() = Calendar(id, monday, tuesday, wednesday, thursday, friday, saturday, sunday, startDate, endDate)
}
