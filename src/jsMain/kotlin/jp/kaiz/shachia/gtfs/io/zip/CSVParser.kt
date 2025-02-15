package jp.kaiz.shachia.gtfs.io.zip

import jp.kaiz.shachia.gtfs.GTFS
import jp.kaiz.shachia.gtfs.js.data.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.js.json

suspend inline fun <T> parseSingleDataCsv(csvString: String, crossinline transformer: (String, String) -> Any?): T {
    val rows = parseCsvAsyncSuspend<T>(csvString, transformer)

    val first = rows.first()
    return first
}

suspend inline fun <T> parseCsvList(csvString: String, crossinline transformer: (String, String) -> Any?): List<T> {
    val rows = parseCsvAsyncSuspend<T>(csvString, transformer)
    return rows.toList()
}

suspend inline fun <T> parseCsvAsyncSuspend(
    csvString: String,
    crossinline transformer: (String, String) -> Any?
): Array<T> =
    suspendCoroutine { cont ->
        Papa.parse(
            csvString, json(
                "header" to true,
                "dynamicTyping" to false,
                "skipEmptyLines" to true,
                "worker" to false,
                "complete" to { results: dynamic, file: dynamic ->
                    cont.resume(results.data as Array<T>)
                },
                "error" to { error: Throwable? ->
                    if (error != null) {
                        cont.resumeWithException(error)
                    } else {
                        cont.resumeWithException(Exception("Error occurred while parsing csv string"))
                    }
                },
                "transform" to { value: String, colName: String ->
                    transformer(value, colName)
                }
            )
        )
    }

suspend fun GTFS.Companion.readFromZipEntries(zipEntries: List<ZipEntry>): JsGTFS {
    val agencyCsvString = zipEntries.find { it.fileName == "agency.txt" }!!.data.decodeToString()
    val agency = parseSingleDataCsv<JsAgency>(agencyCsvString, ::transformJsAgencyValue)

    val stopsCsvString = zipEntries.find { it.fileName == "stops.txt" }!!.data.decodeToString()
    val stops = parseCsvList<JsStop>(stopsCsvString, ::transformJsStopValue)

    val routesCsvString = zipEntries.find { it.fileName == "routes.txt" }!!.data.decodeToString()
    val routes = parseCsvList<JsRoute>(routesCsvString, ::transformJsRouteValue)

    val tripsCsvString = zipEntries.find { it.fileName == "trips.txt" }!!.data.decodeToString()
    val trips = parseCsvList<JsTrip>(tripsCsvString, ::transformJsTripValue)

    val stopTimesCsvString = zipEntries.find { it.fileName == "stop_times.txt" }!!.data.decodeToString()
    val stopTimes = parseCsvList<JsStopTime>(stopTimesCsvString, ::transformJsStopTimeValue)

    val calendarCsvString = zipEntries.find { it.fileName == "calendar.txt" }!!.data.decodeToString()
    val calendar = parseCsvList<JsCalendar>(calendarCsvString, ::transformJsCalendarValue)

    val feedInfoCsvString = zipEntries.find { it.fileName == "feed_info.txt" }!!.data.decodeToString()
    val feedInfo = parseSingleDataCsv<JsFeedInfo>(feedInfoCsvString, ::transformJsFeedInfoValue)

    val gtfs = JsGTFS(agency, stops, routes, trips, stopTimes, calendar, feedInfo)

    return gtfs
}
