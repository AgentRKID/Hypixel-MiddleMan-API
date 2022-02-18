package games.scorpio.api.cache.impl.statistics

import com.google.gson.JsonElement
import games.scorpio.api.API
import games.scorpio.api.cache.Cache
import games.scorpio.api.cache.impl.statistics.impl.SkywarsStatistics
import games.scorpio.api.util.HttpHandler
import java.util.*
import kotlin.collections.HashMap
import kotlin.reflect.KClass

class StatisticsCache : Cache() {

    private val statistics: MutableMap<UUID, MutableMap<Type, GameStatistics?>> = HashMap()

    fun <T : GameStatistics> findOrFetchStatistics(uuid: UUID, type: Type): T {
        if (statistics.containsKey(uuid)) {
            return statistics[uuid]?.get(type) as T
        }

        API.logger.info("Fetching the stats for $uuid")

        val requestObject: JsonElement? = HttpHandler.get("https://api.hypixel.net/player?uuid=$uuid&key=keyHere", null) { response, code ->
            if (code == 404) {
                return@get null
            }
            return@get API.gson.fromJson(response.body?.string(), JsonElement::class.java)
        }

        val profile = requestObject?.asJsonObject?.get("player")?.asJsonObject
        val stats = profile?.get("stats")?.asJsonObject

        // Go through all statistics and gather all the data needed...
        for (type in Type.values()) {
            if (type.clazz == null) {
                continue
            }

            val gameModeObject = stats?.get(type.objectName)?.asJsonObject
            val statistics = type.clazz.java.newInstance()

            if (gameModeObject != null) {
                statistics.gatherStatistics(gameModeObject)
            }
            this.statistics.computeIfAbsent(uuid) { HashMap() } [type] = statistics
        }
        return this.statistics[uuid]?.get(type) as T
    }

    enum class Type(val objectName: String, val clazz: KClass<out GameStatistics>?) {
        SKYWARS("SkyWars", SkywarsStatistics::class),
        BEDWARS("BedWars", null);
    }

}