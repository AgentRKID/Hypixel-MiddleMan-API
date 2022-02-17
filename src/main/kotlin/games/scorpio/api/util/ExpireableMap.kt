package games.scorpio.api.util

import java.util.concurrent.ConcurrentHashMap
import java.util.function.Supplier

class ExpireableMap<K, V>(var backingMap: MutableMap<K, V>, val expiryTime: Long) {

    val timings: MutableMap<K, Long> = ConcurrentHashMap();

    constructor(supplier: Supplier<MutableMap<K, V>>, expiryTime: Long) : this(supplier.get(), expiryTime)

    fun put(key: K, value: V): V? {
        this.checkTimings()
        this.timings[key] = System.currentTimeMillis()
        return backingMap.put(key, value)
    }

    fun get(key: K): V? {
        this.checkTimings()
        return backingMap[key];
    }

    fun remove(key: K): V? {
        return backingMap.remove(key)
    }

    private fun checkTimings() {
        for (entry in timings.entries) {
            val key = entry.key
            val time = this.timings[key]

            if (time == null || System.currentTimeMillis() > time + this.expiryTime) {
                this.remove(key)
                this.timings.remove(key)
            }
        }
    }

}