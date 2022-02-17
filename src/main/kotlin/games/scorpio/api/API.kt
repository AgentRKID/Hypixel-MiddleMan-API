package games.scorpio.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import games.scorpio.api.route.EndpointType
import games.scorpio.api.route.Route
import games.scorpio.api.route.impl.PingRoute
import games.scorpio.api.util.JsonTransformer
import spark.Spark
import java.util.logging.Logger

class API {

    companion object {
        val logger: Logger = Logger.getLogger("API", null)
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()

        @JvmStatic
        fun main(args: Array<String>) {
            API()
        }

    }

    init {
        logger.info("Initializing Hypixel Middle Man API")

        Spark.port(90)

        val routes: List<Route> = arrayListOf(PingRoute())
        val transformer = JsonTransformer()

        Spark.path("/api") {
            for (route in routes) {
                for (entry in route.endpointTypes.entries) {
                    val endpoint = route.endpoints[entry.key] ?: continue
                    val routing = "/${route.routing}${if (entry.key == null || entry.key.isBlank()) "" else "/${entry.key}"}"

                    if (entry.value == EndpointType.GET) {
                        Spark.get(routing) { request, response ->
                            if (route.transform) {
                                transformer.render(endpoint.invoke(request, response))
                            } else {
                                endpoint.invoke(request, response)
                            }
                        }
                    } else {
                        Spark.post(routing) { request, response ->
                            if (route.transform) {
                                transformer.render(endpoint.invoke(request, response))
                            } else {
                                endpoint.invoke(request, response)
                            }
                        }
                    }
                    logger.info("Created route: ${routing}, with type: ${entry.value}.")
                }
            }
        }
        logger.info("Finished loading API")
    }

}