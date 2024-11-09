package jp.kaiz.shachia.gtfs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StopTime(
    @SerialName("trip_id")
    val tripId: String,

    @SerialName("arrival_time")
    val arrivalTime: String?,

    @SerialName("departure_time")
    val departureTime: String?,

    @SerialName("stop_id")
    val stopId: String?,

    @SerialName("location_group_id")
    val locationGroupId: String?,

    @SerialName("location_id")
    val locationId: String?,

    @SerialName("stop_sequence")
    val stopSequence: Int,

    @SerialName("stop_headsign")
    val stopHeadsign: String?,

    @SerialName("start_pickup_drop_off_window")
    val startPickupDropOffWindow: String?,

    @SerialName("end_pickup_drop_off_window")
    val endPickupDropOffWindow: String?,

    @SerialName("pickup_type")
    val pickupType: PickupType?,

    @SerialName("drop_off_type")
    val dropOffType: DropOffType?,

//    @SerialName("continuous_pickup")
//    val continuousPickup: ContinuousPickup?,

//    @SerialName("continuous_drop_off")
//    val continuousDropOff: ContinuousDropOff?,

    @SerialName("shape_dist_traveled")
    val shapeDistTraveled: Float?,

    @SerialName("timepoint")
    val timepoint: Int?,
) {
    enum class PickupType(val value: Int) {
        REGULAR(0),
        NONE(1),
        MUST_PHONE_AGENCY(2),
        MUST_COORDINATE_WITH_DRIVER(3),
    }

    enum class DropOffType(val value: Int) {
        REGULAR(0),
        NONE(1),
        MUST_PHONE_AGENCY(2),
        MUST_COORDINATE_WITH_DRIVER(3),
    }
}

fun stopTime(init: StopTimeBuilder.() -> Unit) = StopTimeBuilder().apply(init).build()

class StopTimeBuilder {
    var tripId: String = ""
    var arrivalTime: String? = null
    var departureTime: String? = null
    var stopId: String? = null
    var locationGroupId: String? = null
    var locationId: String? = null
    var stopSequence: Int = 0
    var stopHeadsign: String? = null
    var startPickupDropOffWindow: String? = null
    var endPickupDropOffWindow: String? = null
    var pickupType: StopTime.PickupType? = null
    var dropOffType: StopTime.DropOffType? = null
    var shapeDistTraveled: Float? = null
    var timepoint: Int? = null

    fun build() = StopTime(
        tripId,
        arrivalTime,
        departureTime,
        stopId,
        locationGroupId,
        locationId,
        stopSequence,
        stopHeadsign,
        startPickupDropOffWindow,
        endPickupDropOffWindow,
        pickupType,
        dropOffType,
        shapeDistTraveled,
        timepoint
    )
}