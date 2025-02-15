package jp.kaiz.shachia.gtfs.js.data

data class JsGTFS(
    val agency: JsAgency,
//    val agencyJp: AgencyJp,
    val stops: List<JsStop>,
    val routes: List<JsRoute>,
//    val routesJp: List<RouteJp>,
    val trips: List<JsTrip>,
//    val officeJp: List<OfficeJp>,
    val stopTimes: List<JsStopTime>,
    val calendar: List<JsCalendar>,
//    val calendarDates: List<CalendarDate>,
//    val fareAttributes: List<FareAttribute>,
//    val fareRules: List<FareRule>,
//    val shapes: List<Shape>,
//    val frequencies: List<Frequency>,
//    val transfers: List<Transfer>,
    val feedInfo: JsFeedInfo,
//    val translations: List<Translation>,
)
