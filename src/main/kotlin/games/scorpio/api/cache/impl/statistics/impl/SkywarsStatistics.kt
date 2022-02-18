package games.scorpio.api.cache.impl.statistics.impl

import com.google.gson.JsonObject
import games.scorpio.api.cache.impl.statistics.GameStatistics

class SkywarsStatistics : GameStatistics {

    var gamesPlayed: Int = 0
    var kills: Int = 0
    var deaths: Int = 0
    var wins: Int = 0
    var losses: Int = 0
    var quits: Int = 0


    override fun gatherStatistics(obj: JsonObject) {
        gamesPlayed = obj["games_played_skywars"].asInt
        kills = obj["kills"].asInt
        deaths = obj["deaths"].asInt
        wins = obj["wins"].asInt
        losses = obj["losses"].asInt
        quits = obj["quits"].asInt
    }

}