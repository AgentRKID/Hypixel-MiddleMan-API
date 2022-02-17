package games.scorpio.api.cache.impl.statistics

import com.google.gson.JsonObject

interface GameStatistics {

    fun gatherStatistics(obj: JsonObject)

}