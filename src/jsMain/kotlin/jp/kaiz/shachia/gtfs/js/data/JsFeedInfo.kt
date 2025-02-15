package jp.kaiz.shachia.gtfs.js.data

external interface JsFeedInfo {
    @JsName("feed_publisher_name")
    val publisherName: String

    @JsName("feed_publisher_url")
    val publisherUrl: String

    @JsName("feed_lang")
    val lang: String

    @JsName("default_lang")
    val defaultLang: String?

    @JsName("feed_start_date")
    val startDate: String?

    @JsName("feed_end_date")
    val endDate: String?

    @JsName("feed_version")
    val version: String?

    @JsName("feed_contact_email")
    val contactEmail: String?

    @JsName("feed_contact_url")
    val contactUrl: String?
}

inline fun transformJsFeedInfoValue(value: String, colName: String): Any? {
    return if (value.isEmpty()) null else value
}