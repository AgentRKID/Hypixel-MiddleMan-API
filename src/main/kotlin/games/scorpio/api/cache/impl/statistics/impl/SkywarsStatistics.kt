package games.scorpio.api.cache.impl.statistics.impl

import com.google.gson.JsonObject
import games.scorpio.api.cache.impl.statistics.GameStatistics

class SkywarsStatistics : GameStatistics {

    var gamesPlayed: Int = 0
    var kills: Int = 0
    var overallDeaths: Int = 0
    var soloDeaths: Int = 0
    var soloInsaneDeaths: Int = 0
    var wins: Int = 0
    var overallLosses: Int = 0
    var quits: Int = 0


    override fun gatherStatistics(obj: JsonObject) {
        gamesPlayed = obj["games_played_skywars"].asInt
        kills = obj["kills"].asInt
        overallDeaths = obj["overall_deaths"].asInt
        soloDeaths = obj["solo_deaths"].asInt
        soloInsaneDeaths = obj["solo_insane_deaths"].asInt
        wins = obj["wins"].asInt
        overallLosses = obj["overall_losses"].asInt
        quits = obj["quits"].asInt
    }

}