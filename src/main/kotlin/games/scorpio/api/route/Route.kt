package games.scorpio.api.route

import spark.Request
import spark.Response
import java.util.function.BiFunction

open class Route(val routing: String, val transform: Boolean) {

    val endpoints: MutableMap<String, (Request, Response) -> Any?> = HashMap()
    val endpointTypes: MutableMap<String, EndpointType> = HashMap()

    fun addEndpoint(route: String, endpointType: EndpointType, endpoint: (Request, Response) -> Any?) {

        this.endpoints[route] = endpoint
        this.endpointTypes[route] = endpointType
    }

}