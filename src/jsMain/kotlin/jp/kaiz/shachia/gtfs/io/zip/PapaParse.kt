package jp.kaiz.shachia.gtfs.io.zip

@JsModule("papaparse")
@JsNonModule
external object Papa {
    fun parse(
        csvString: String,
        config: dynamic = definedExternally,
    ): dynamic
}