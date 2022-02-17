package games.scorpio.api.route.impl

import games.scorpio.api.route.EndpointType
import games.scorpio.api.route.Route
import spark.Spark
import java.lang.Exception
import java.util.*

class StatisticsRoute : Route("stats", true) {

    init {
        addEndpoint(":uuid", EndpointType.GET) { request, response ->
            val uuid: UUID = try {
                UUID.fromString(request.params("uuid"))
            } catch (ex: Exception) {
                null
            } ?: return@addEndpoint Spark.halt(400, "Malformed UUID")

            "UUID - $uuid"
        }
    }

}