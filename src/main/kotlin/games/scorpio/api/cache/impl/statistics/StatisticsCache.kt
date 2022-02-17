package games.scorpio.api.cache.impl.statistics

import games.scorpio.api.cache.Cache

class StatisticsCache : Cache() {

    val statistics: MutableMap<Type, GameStatistics> = HashMap()

    enum class Type {
        SKYWARS,
        BEDWARS
    }

}