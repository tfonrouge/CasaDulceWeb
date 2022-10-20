package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.model.SLocation
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.bson.BsonDocument
import org.litote.kmongo.json

class ApiShopifyService {

    companion object {
        private const val apiVersion = "2020-01"
        const val baseURI = "https://casa-dulce-usa.myshopify.com/admin/api/${apiVersion}/"
        val httpClient = HttpClient(CIO) {
            install(ContentNegotiation)
            install(ContentEncoding)
            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
            }
            install(Auth) {
                basic {
                    credentials {
                        BasicAuthCredentials(
                            username = "5e4475611e3edd00eb0e3f02450f604a",
                            password = "354a516d11726d08e6a12c5d4be7034e"
                        )
                    }
                    realm = "Access to the '/' path"
                    sendWithoutRequest {
                        true
                    }
                }
            }
        }
        var locationId: Long = 0
    }

    @OptIn(InternalAPI::class)
    suspend fun taskGetItems(page: Int?, size: Int?) {
        var response = httpClient.get("${baseURI}/locations.json") {
            println(this)
        }
        println(response)
        val content = response.content
        val locations = content.readUTF8Line()?.let { contentLine ->
            BsonDocument.parse(contentLine)["locations"]?.json?.let {
                Json.decodeFromString<Array<SLocation>>(it)
            }
        }
        if (locations.isNullOrEmpty()) return
        val location0 = locations[0]

        println(locations)

        locationId = location0.id
        var continueFlag = true
        var nextLink: String? = null
        var callUrl = baseURI

        while (continueFlag) {
            val path: String
            if (nextLink != null) {
                path = "?" + nextLink.split("?")[1]
                callUrl = nextLink.split("?")[0]
            } else {
                path = "products.json?limit=$size"
                callUrl = baseURI
            }
            response = httpClient.get("$callUrl/$path")
            response.content.read {
                val s = String(it.moveToByteArray())
                println(s)
            }
            println(response)
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
}
