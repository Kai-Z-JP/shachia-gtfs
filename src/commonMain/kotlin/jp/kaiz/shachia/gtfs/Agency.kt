package jp.kaiz.shachia.gtfs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Agency(
    @SerialName("agency_id")
    val id: String,

    @SerialName("agency_name")
    val name: String,

    @SerialName("agency_url")
    val url: String,

    @SerialName("agency_timezone")
    val timeZone: String,

    @SerialName("agency_lang")
    val lang: String?,

    @SerialName("agency_phone")
    val phone: String?,

    @SerialName("agency_fare_url")
    val fareUrl: String?,

    @SerialName("agency_email")
    val email: String?
)

fun agency(init: AgencyBuilder.() -> Unit) = AgencyBuilder().apply(init).build()

class AgencyBuilder {
    var id: String = ""
    var name: String = ""
    var url: String = ""
    var timeZone: String = ""
    var lang: String? = null
    var phone: String? = null
    var fareUrl: String? = null
    var email: String? = null

    fun build() = Agency(id, name, url, timeZone, lang, phone, fareUrl, email)
}