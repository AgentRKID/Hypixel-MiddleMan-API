package games.scorpio.api.util

import games.scorpio.api.API
import spark.ResponseTransformer

class JsonTransformer : ResponseTransformer {

    override fun render(model: Any?): String {
        return API.gson.toJson(model)
    }

}