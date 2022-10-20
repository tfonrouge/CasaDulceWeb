package com.fonrouge.remoteScreen.services

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiShopifyService {

    companion object {
        private val username: String = "5e4475611e3edd00eb0e3f02450f604a"
        private val password: String = "354a516d11726d08e6a12c5d4be7034e"
        private const val apiVersion = "2020-01"
        const val baseURI = "https://casa-dulce-usa.myshopify.com/admin/api/${apiVersion}/"
        val httpClient = HttpClient(CIO) {
            install(ContentNegotiation)
            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
            }

        }
        var locationId: String = ""
    }

    suspend fun taskGetItems() {
        var response = httpClient.get("${baseURI}/locations.json") {
            println(this)
        }
        println(response)
//        val one = response.one()
//        locationId = one.getJsonArray("locations")[0].asJsonObject()["id"].toString()
//        var continueFlag = true
//        var nextLink: String? = null
//
//        while (continueFlag) {
//            val path: String
//            if (nextLink != null) {
//                httpClient.baseURI = nextLink.split("?")[0]
//                path = "?" + nextLink.split("?")[1]
//            } else {
//                httpClient.baseURI = baseURI
//                path = "products.json?limit=250"
//            }
//            response = httpClient.get(path)
//            val array: JsonArray = response.list()[0].asJsonObject()["products"] as JsonArray
//            Platform.runLater {
//                borderPaneShopify.tableView.items.addAll(array.toModel())
//            }
//            val headers = response.headers
//            nextLink = null
//            continueFlag = headers.containsKey("Link")
//            if (continueFlag) {
//                val links = headers["Link"]?.get(0)?.split(",")
//                links?.forEach { link: String ->
//                    if (link.contains("rel=\"next\"")) {
//                        nextLink = link.split(";")[0].split("<", ">")[1]
//                    }
//                }
//            }
//            continueFlag = nextLink != null
    }

    /* TODO: check how to get items list cost bypassing the limit of 'ids' required parameter */
//        taskGetItemsCost()
}
