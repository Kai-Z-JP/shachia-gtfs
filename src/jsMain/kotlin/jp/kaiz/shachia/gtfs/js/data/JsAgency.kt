package jp.kaiz.shachia.gtfs.js.data


external interface JsAgency {
    @JsName("agency_id")
    val id: String

    @JsName("agency_name")
    val name: String

    @JsName("agency_url")
    val url: String

    @JsName("agency_timezone")
    val timeZone: String

    @JsName("agency_lang")
    val lang: String?

    @JsName("agency_phone")
    val phone: String?

    @JsName("agency_fare_url")
    val fareUrl: String?

    @JsName("agency_email")
    val email: String?
}

inline fun transformJsAgencyValue(value: String, colName: String): Any? {
    return if (value.isEmpty()) null else value
}