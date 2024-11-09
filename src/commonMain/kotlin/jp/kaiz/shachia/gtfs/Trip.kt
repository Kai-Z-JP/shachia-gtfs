package jp.kaiz.shachia.gtfs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    @SerialName("route_id")
    val routeId: String,

    @SerialName("service_id")
    val serviceId: String,

    @SerialName("trip_id")
    val tripId: String,

    @SerialName("trip_headsign")
    val headSign: String?,

    @SerialName("trip_short_name")
    val shortName: String?,

    @SerialName("direction_id")
    val directionId: Int?,

    @SerialName("block_id")
    val blockId: String?,

    @SerialName("shape_id")
    val shapeId: String?,

    @SerialName("wheelchair_accessible")
    val wheelchairAccessible: Int?,

    @SerialName("bikes_allowed")
    val bikesAllowed: Int?
)

fun trip(init: TripBuilder.() -> Unit) = TripBuilder().apply(init).build()

class TripBuilder {
    var routeId: String = ""
    var serviceId: String = ""
    var tripId: String = ""
    var tripHeadSign: String? = null
    var tripShortName: String? = null
    var directionId: Int? = null
    var blockId: String? = null
    var shapeId: String? = null
    var wheelchairAccessible: Int? = null
    var bikesAllowed: Int? = null

    fun build() = Trip(
        routeId,
        serviceId,
        tripId,
        tripHeadSign,
        tripShortName,
        directionId,
        blockId,
        shapeId,
        wheelchairAccessible,
        bikesAllowed
    )
}
