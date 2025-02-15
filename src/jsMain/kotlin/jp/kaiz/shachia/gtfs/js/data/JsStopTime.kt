package jp.kaiz.shachia.gtfs.js.data

external interface JsStopTime {
    @JsName("trip_id")
    val tripId: String

    @JsName("arrival_time")
    val arrivalTime: String?

    @JsName("departure_time")
    val departureTime: String?

    @JsName("stop_id")
    val stopId: String?

    @JsName("location_group_id")
    val locationGroupId: String?

    @JsName("location_id")
    val locationId: String?

    @JsName("stop_sequence")
    val stopSequence: Int

    @JsName("stop_headsign")
    val stopHeadsign: String?

    @JsName("start_pickup_drop_off_window")
    val startPickupDropOffWindow: String?

    @JsName("end_pickup_drop_off_window")
    val endPickupDropOffWindow: String?
//    @JsName("continuous_pickup")
//    val continuousPickup: ContinuousPickup?

//    @JsName("continuous_drop_off")
//    val continuousDropOff: ContinuousDropOff?

    @JsName("shape_dist_traveled")
    val shapeDistTraveled: Float?

    @JsName("timepoint")
    val timepoint: Int?
}

inline fun transformJsStopTimeValue(value: String, colName: String): Any? {
    return when (colName) {
        "shape_dist_traveled" -> value.toFloatOrNull()
        "stop_sequence" -> value.toInt()
        "timepoint" -> value.toIntOrNull()
        else -> if (value.isEmpty()) null else value
    }
}


