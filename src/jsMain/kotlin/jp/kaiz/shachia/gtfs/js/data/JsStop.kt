package jp.kaiz.shachia.gtfs.js.data

external interface JsStop {
    @JsName("stop_id")
    val stopId: String

    @JsName("stop_code")
    val code: String?

    @JsName("stop_name")
    val name: String?

    @JsName("tts_stop_name")
    val ttsStopName: String?

    @JsName("stop_desc")
    val desc: String?

    @JsName("stop_lat")
    val lat: Double?

    @JsName("stop_lon")
    val lon: Double?

    @JsName("zone_id")
    val zoneId: String?

    @JsName("stop_url")
    val url: String?

    @JsName("parent_station")
    val parentStation: String?

    @JsName("stop_timezone")
    val timezone: String?

    @JsName("wheelchair_boarding")
    val wheelchairBoarding: Int?

    @JsName("level_id")
    val levelId: String?

    @JsName("platform_code")
    val platformCode: String?
}

inline fun transformJsStopValue(value: String, colName: String): Any? {
    return when (colName) {
        "lat", "lon" -> value.toDoubleOrNull()
        "wheelchair_boarding" -> value.toIntOrNull()
        else -> if (value.isEmpty()) null else value
    }
}


