package jp.kaiz.shachia.gtfs

import app.softwork.serialization.csv.CSVFormat
import jp.kaiz.shachia.gtfs.csv.CSVUnSequentiallyFormat
import jp.kaiz.shachia.gtfs.io.zip.ZipEntry
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class GTFS(
    val agency: Agency,
//    val agencyJp: AgencyJp,
    val stops: List<Stop>,
    val routes: List<Route>,
//    val routesJp: List<RouteJp>,
    val trips: List<Trip>,
//    val officeJp: List<OfficeJp>,
    val stopTimes: List<StopTime>,
    val calendar: List<Calendar>,
//    val calendarDates: List<CalendarDate>,
//    val fareAttributes: List<FareAttribute>,
//    val fareRules: List<FareRule>,
//    val shapes: List<Shape>,
//    val frequencies: List<Frequency>,
//    val transfers: List<Transfer>,
    val feedInfo: FeedInfo,
//    val translations: List<Translation>,
) {
    fun exportToZip(fileName: String) {
        val agencyCsvString = CSVFormat.encodeToString(Agency.serializer(), agency)
        val stopsCsvString = CSVFormat.encodeToString(ListSerializer(Stop.serializer()), stops)
        val routesCsvString = CSVFormat.encodeToString(ListSerializer(Route.serializer()), routes)
        val tripsCsvString = CSVFormat.encodeToString(ListSerializer(Trip.serializer()), trips)
        val stopTimesCsvString = CSVFormat.encodeToString(ListSerializer(StopTime.serializer()), stopTimes)
        val calendarCsvString = CSVFormat.encodeToString(ListSerializer(Calendar.serializer()), calendar)
        val feedInfoCsvString = CSVFormat.encodeToString(FeedInfo.serializer(), feedInfo)

    }

    companion object {
        fun readFromZip(zipEntries: List<ZipEntry>): GTFS {
            val agencyCsvString = zipEntries.find { it.fileName == "agency.txt" }!!.data.decodeToString()
            val agency = CSVUnSequentiallyFormat.decodeFromString(Agency.serializer(), agencyCsvString)

            val stopsCsvString = zipEntries.find { it.fileName == "stops.txt" }!!.data.decodeToString()
            val stops = CSVUnSequentiallyFormat.decodeFromString(ListSerializer(Stop.serializer()), stopsCsvString)

            val routesCsvString = zipEntries.find { it.fileName == "routes.txt" }!!.data.decodeToString()
            val routes = CSVUnSequentiallyFormat.decodeFromString(ListSerializer(Route.serializer()), routesCsvString)

            val tripsCsvString = zipEntries.find { it.fileName == "trips.txt" }!!.data.decodeToString()
            val trips = CSVUnSequentiallyFormat.decodeFromString(ListSerializer(Trip.serializer()), tripsCsvString)

            val stopTimesCsvString = zipEntries.find { it.fileName == "stop_times.txt" }!!.data.decodeToString()
            val stopTimes =
                CSVUnSequentiallyFormat.decodeFromString(ListSerializer(StopTime.serializer()), stopTimesCsvString)

            val calendarCsvString = zipEntries.find { it.fileName == "calendar.txt" }!!.data.decodeToString()
            val calendar =
                CSVUnSequentiallyFormat.decodeFromString(ListSerializer(Calendar.serializer()), calendarCsvString)

            val feedInfoCsvString = zipEntries.find { it.fileName == "feed_info.txt" }!!.data.decodeToString()
            val feedInfo = CSVUnSequentiallyFormat.decodeFromString(FeedInfo.serializer(), feedInfoCsvString)

            return GTFS(agency, stops, routes, trips, stopTimes, calendar, feedInfo)
        }
    }
}

fun gtfs(init: GTFSBuilder.() -> Unit) = GTFSBuilder().apply(init).build()

class GTFSBuilder internal constructor() {
    private var agency: Agency? = null
    private var stops: List<Stop>? = null
    private var routes: List<Route>? = null
    private var trips: List<Trip>? = null
    private var stopTimes: List<StopTime>? = null
    private var calendar: List<Calendar>? = null
    private var feedInfo: FeedInfo? = null

    fun build() = GTFS(
        agency = checkNotNull(agency),
        stops = checkNotNull(stops),
        routes = checkNotNull(routes),
        trips = checkNotNull(trips),
        stopTimes = checkNotNull(stopTimes),
        calendar = checkNotNull(calendar),
        feedInfo = checkNotNull(feedInfo),
    )

    fun agency(init: AgencyBuilder.() -> Unit) {
        agency = AgencyBuilder().apply(init).build()
    }

    fun stop(init: StopBuilder.() -> Unit) {
        stops = stops?.plus(StopBuilder().apply(init).build())
    }

    fun route(init: RouteBuilder.() -> Unit) {
        routes = routes?.plus(RouteBuilder().apply(init).build())
    }

    fun trip(init: TripBuilder.() -> Unit) {
        trips = trips?.plus(TripBuilder().apply(init).build())
    }

    fun stopTime(init: StopTimeBuilder.() -> Unit) {
        stopTimes = stopTimes?.plus(StopTimeBuilder().apply(init).build())
    }

    fun calendar(init: CalendarBuilder.() -> Unit) {
        calendar = calendar?.plus(CalendarBuilder().apply(init).build())
    }

    fun feedInfo(init: FeedInfoBuilder.() -> Unit) {
        feedInfo = FeedInfoBuilder().apply(init).build()
    }
}