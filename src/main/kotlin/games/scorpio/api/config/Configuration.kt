package games.scorpio.api.config

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import games.scorpio.api.API
import games.scorpio.api.util.FileUtil
import java.io.File

class Configuration {

    companion object {
        private val config: File = File("config" + File.separator + "config.json")
    }

    var port: Int = 9200

    init {
        FileUtil.read(config) {
            val element = API.gson.fromJson(it, JsonElement::class.java)

            if (element == null || !element.isJsonObject) {
                return@read
            }
            port = element.asJsonObject["Port"].asInt
        }
    }

    fun save() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("Port", port)
        FileUtil.write(API.gson.toJson(jsonObject), config)
    }

}