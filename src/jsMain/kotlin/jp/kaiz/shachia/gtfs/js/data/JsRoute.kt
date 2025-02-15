package jp.kaiz.shachia.gtfs.js.data

external interface JsRoute {
    @JsName("route_id")
    val routeId: String

    @JsName("agency_id")
    val agencyId: String?

    @JsName("route_short_name")
    val shortName: String?

    @JsName("route_long_name")
    val longName: String?

    @JsName("route_desc")
    val desc: String?

    @JsName("route_url")
    val url: String?

    @JsName("route_color")
    val color: String?

    @JsName("route_text_color")
    val textColor: String?

    @JsName("route_sort_order")
    val sortOrder: Int?

//    @JsName("continuous_pickup")
//    val continuousPickup: ContinuousPickup?
//
//    @JsName("continuous_drop_off")
//    val continuousDropOff: ContinuousDropOff?

    @JsName("network_id")
    val networkId: String?
}

inline fun transformJsRouteValue(value: String, colName: String): Any? {
    return when (colName) {
        "route_sort_order" -> value.toIntOrNull()
        else -> if (value.isEmpty()) null else value
    }
}