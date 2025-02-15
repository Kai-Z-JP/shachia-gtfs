package jp.kaiz.shachia.gtfs.js.data

external interface JsCalendar {
    @JsName("service_id")
    val id: String

    @JsName("monday")
    val monday: Int

    @JsName("tuesday")
    val tuesday: Int

    @JsName("wednesday")
    val wednesday: Int

    @JsName("thursday")
    val thursday: Int

    @JsName("friday")
    val friday: Int

    @JsName("saturday")
    val saturday: Int

    @JsName("sunday")
    val sunday: Int

    @JsName("start_date")
    val startDate: String

    @JsName("end_date")
    val endDate: String
}

inline fun transformJsCalendarValue(value: String, colName: String): Any? {
    return when (colName) {
        "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday" -> value.toInt()
        else -> value
    }
}