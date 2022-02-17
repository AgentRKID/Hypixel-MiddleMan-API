package games.scorpio.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import games.scorpio.api.config.Configuration
import games.scorpio.api.route.EndpointType
import games.scorpio.api.route.Route
import games.scorpio.api.route.impl.PingRoute
import games.scorpio.api.route.impl.StatisticsRoute
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

        val configuration = Configuration()

        Runtime.getRuntime().addShutdownHook(Thread {
            configuration.save()
        })

        Spark.port(configuration.port)

        logger.info("API Port is ${configuration.port}.")

        val routes: List<Route> = arrayListOf(PingRoute(), StatisticsRoute())
        val transformer = JsonTransformer()

        Spark.before("/*") { request, response ->
            if (request.contentType() != null && (request.contentType() != "application/json" || request.contentType() != "application/json; charset=UTF-8")) {
                Spark.halt(400, "Bad request")
            }
        }

        logger.info("Spark can only read \"application/json\" content type now.")

        Spark.exception(Exception::class.java) { exception, request, response ->
            logger.warning("Error 500 - " + request.pathInfo() + ", Body: " + request.body())
        }

        logger.info("Spark exception handler has been setup.")

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