package jp.kaiz.shachia.gtfs.js.data

external interface JsTrip {
    @JsName("route_id")
    val routeId: String

    @JsName("service_id")
    val serviceId: String

    @JsName("trip_id")
    val tripId: String

    @JsName("trip_headsign")
    val headSign: String?

    @JsName("trip_short_name")
    val shortName: String?

    @JsName("direction_id")
    val directionId: Int?

    @JsName("block_id")
    val blockId: String?

    @JsName("shape_id")
    val shapeId: String?

    @JsName("wheelchair_accessible")
    val wheelchairAccessible: Int?

    @JsName("bikes_allowed")
    val bikesAllowed: Int?
}

inline fun transformJsTripValue(value: String, colName: String): Any? {
    return when (colName) {
        "direction_id", "wheelchair_accessible", "bikes_allowed" -> value.toIntOrNull()
        else -> if (value.isEmpty()) null else value
    }
}
