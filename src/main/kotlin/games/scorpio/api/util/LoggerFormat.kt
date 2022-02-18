package games.scorpio.api.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Handler
import java.util.logging.LogRecord


class LoggerFormat : Handler() {

    private val DATE_FORMAT: DateFormat = SimpleDateFormat("EEE hh:mm aaa")

    override fun publish(record: LogRecord?) {
        println(String.format("[%s] [%s] %s", DATE_FORMAT.format(Date()), record!!.level.name, record.message))
    }

    override fun flush() {}

    override fun close() {
    }

}