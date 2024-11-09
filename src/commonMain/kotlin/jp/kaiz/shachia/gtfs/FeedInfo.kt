package jp.kaiz.shachia.gtfs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedInfo(
    @SerialName("feed_publisher_name")
    val publisherName: String,

    @SerialName("feed_publisher_url")
    val publisherUrl: String,

    @SerialName("feed_lang")
    val lang: String,

    @SerialName("default_lang")
    val defaultLang: String?,

    @SerialName("feed_start_date")
    val startDate: String?,

    @SerialName("feed_end_date")
    val endDate: String?,

    @SerialName("feed_version")
    val version: String?,

    @SerialName("feed_contact_email")
    val contactEmail: String?,

    @SerialName("feed_contact_url")
    val contactUrl: String?
)

fun feedInfo(init: FeedInfoBuilder.() -> Unit) = FeedInfoBuilder().apply(init).build()

class FeedInfoBuilder {
    var publisherName: String = ""
    var publisherUrl: String = ""
    var lang: String = ""
    var defaultLang: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var version: String? = null
    var contactEmail: String? = null
    var contactUrl: String? = null

    fun build() =
        FeedInfo(publisherName, publisherUrl, lang, defaultLang, startDate, endDate, version, contactEmail, contactUrl)
}