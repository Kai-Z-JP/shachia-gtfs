package jp.kaiz.shachia.gtfs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Route(
    @SerialName("route_id")
    val id: String,

    @SerialName("agency_id")
    val agencyId: String?,

    @SerialName("route_short_name")
    val shortName: String?,

    @SerialName("route_long_name")
    val longName: String?,

    @SerialName("route_desc")
    val desc: String?,

    @SerialName("route_type")
    val type: RouteType,

    @SerialName("route_url")
    val url: String?,

    @SerialName("route_color")
    val color: String?,

    @SerialName("route_text_color")
    val textColor: String?,

    @SerialName("route_sort_order")
    val sortOrder: Int?,

//    @SerialName("continuous_pickup")
//    val continuousPickup: ContinuousPickup?,
//
//    @SerialName("continuous_drop_off")
//    val continuousDropOff: ContinuousDropOff?,

    @SerialName("network_id")
    val networkId: String?,
) {
    enum class RouteType(val value: Int) {
        @SerialName("0")
        TRAM(0),
        @SerialName("1")
        SUBWAY(1),
        @SerialName("2")
        RAIL(2),
        @SerialName("3")
        BUS(3),
        @SerialName("4")
        FERRY(4),
        @SerialName("5")
        CABLE_CAR(5),
        @SerialName("6")
        GONDOLA(6),
        @SerialName("7")
        FUNICULAR(7),
        @SerialName("11")
        TROLLEYBUS(11),
        @SerialName("12")
        MONORAIL(12),
        @SerialName("1501")
        DEMAND(1501)
    }

//    enum class ContinuousPickup(val value: Int) {
//        NOT_AVAILABLE(0),
//        MUST_PHONE_AGENCY(1),
//        MUST_COORDINATE_WITH_DRIVER(2),
//        MUST_COORDINATE_WITH_DRIVER_TO_ARRANGE_CONTINUOUS_STOPPING_PICKUP(3),
//    }
//
//    enum class ContinuousDropOff(val value: Int) {
//        NOT_AVAILABLE(0),
//        MUST_PHONE_AGENCY(1),
//        MUST_COORDINATE_WITH_DRIVER(2),
//        MUST_COORDINATE_WITH_DRIVER_TO_ARRANGE_CONTINUOUS_STOPPING_DROP_OFF(3),
//    }
}

fun route(init: RouteBuilder.() -> Unit) = RouteBuilder().apply(init).build()

class RouteBuilder {
    var id: String = ""
    var agencyId: String? = null
    var shortName: String? = null
    var longName: String? = null
    var desc: String? = null
    var type: Route.RouteType = Route.RouteType.BUS
    var url: String? = null
    var color: String? = null
    var textColor: String? = null
    var sortOrder: Int? = null

    //    var continuousPickup: Route.ContinuousPickup? = null
    //    var continuousDropOff: Route.ContinuousDropOff? = null
    var networkId: String? = null

    fun build() = Route(
        id,
        agencyId,
        shortName,
        longName,
        desc,
        type,
        url,
        color,
        textColor,
        sortOrder, /*continuousPickup, continuousDropOff,*/
        networkId
    )
}