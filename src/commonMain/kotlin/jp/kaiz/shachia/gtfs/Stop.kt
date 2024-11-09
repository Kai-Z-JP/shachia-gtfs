package jp.kaiz.shachia.gtfs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stop(
    @SerialName("stop_id")
    val id: String,

    @SerialName("stop_code")
    val code: String?,

    @SerialName("stop_name")
    val name: String?,

    @SerialName("tts_stop_name")
    val ttsStopName: String?,

    @SerialName("stop_desc")
    val desc: String?,

    @SerialName("stop_lat")
    val lat: Double?,

    @SerialName("stop_lon")
    val lon: Double?,

    @SerialName("zone_id")
    val zoneId: String?,

    @SerialName("stop_url")
    val url: String?,

    @SerialName("location_type")
    val locationType: LocationType?,

    @SerialName("parent_station")
    val parentStation: String?,

    @SerialName("stop_timezone")
    val timezone: String?,

    @SerialName("wheelchair_boarding")
    val wheelchairBoarding: Int?,

    @SerialName("level_id")
    val levelId: String?,

    @SerialName("platform_code")
    val platformCode: String?,
) {
    enum class LocationType(val value: Int) {
        STOP(0),
        STATION(1),
        ENTRANCE(2),
        GENERIC_NODE(3),
        BOARDING_AREA(4),
    }
}

fun stop(init: StopBuilder.() -> Unit) = StopBuilder().apply(init).build()

class StopBuilder {
    var id: String = ""
    var code: String? = null
    var name: String? = null
    var ttsStopName: String? = null
    var desc: String? = null
    var lat: Double? = null
    var lon: Double? = null
    var zoneId: String? = null
    var url: String? = null
    var locationType: Stop.LocationType? = null
    var parentStation: String? = null
    var timezone: String? = null
    var wheelchairBoarding: Int? = null
    var levelId: String? = null
    var platformCode: String? = null

    fun build() = Stop(
        id,
        code,
        name,
        ttsStopName,
        desc,
        lat,
        lon,
        zoneId,
        url,
        locationType,
        parentStation,
        timezone,
        wheelchairBoarding,
        levelId,
        platformCode
    )
}