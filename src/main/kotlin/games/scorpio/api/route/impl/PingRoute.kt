package games.scorpio.api.route.impl

import games.scorpio.api.route.EndpointType
import games.scorpio.api.route.Route

class PingRoute : Route("ping", false) {

    init {
        addEndpoint("", EndpointType.GET) { _, _ ->
            "Pong"
        }
    }

}