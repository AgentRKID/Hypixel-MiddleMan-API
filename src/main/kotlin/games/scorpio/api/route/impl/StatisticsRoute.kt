package games.scorpio.api.route.impl

import games.scorpio.api.cache.impl.statistics.StatisticsCache
import games.scorpio.api.route.EndpointType
import games.scorpio.api.route.Route
import spark.Spark
import java.lang.Exception
import java.util.*

class StatisticsRoute : Route("stats", true) {

    init {
        val cache = StatisticsCache()

        addEndpoint(":uuid", EndpointType.GET) { request, response ->
            val uuid: UUID = try {
                UUID.fromString(request.params("uuid"))
            } catch (ex: Exception) {
                null
            } ?: return@addEndpoint Spark.halt(400, "Malformed UUID")

            val gameType: StatisticsCache.Type = try {
                StatisticsCache.Type.valueOf(request.queryParamOrDefault("gameType", "SKYWARS"))
            } catch (ex: Exception) {
                null
            } ?: return@addEndpoint Spark.halt(400, "Malformed GameType")
            return@addEndpoint cache.findOrFetchStatistics(uuid, gameType)
        }
    }

}