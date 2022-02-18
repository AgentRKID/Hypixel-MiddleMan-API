package games.scorpio.api.util

import games.scorpio.api.API
import okhttp3.*

object HttpHandler {

    val client = OkHttpClient()

    fun <T> get(url: String, headers: Headers?, gather: (response: Response, code: Int) -> T): T? {
        val builder = Request.Builder()

        if (headers != null) {
            builder.headers(headers)
        }

        val request = builder.url(url).get().build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            gather.invoke(response, 404)
            API.logger.info("Response was not successful")
            return gather.invoke(response, 404)
        }

        if (response.isRedirect) {
            API.logger.info("Redirecting API request")
        }
        return gather.invoke(response, response.code)
    }

    fun <T> post(url: String, body: RequestBody, headers: Headers, finish: (response: Response, code: Int) -> T) {
        val builder = Request.Builder()

        if (headers != null) {
            builder.headers(headers)
        }

        val request = builder.url(url).post(body).build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            finish.invoke(response, 404)
            API.logger.info("Response was not successful")
            return
        }

        if (response.isRedirect) {
            API.logger.info("Redirecting API request")
            return
        }
        finish.invoke(response, response.code)
    }

}