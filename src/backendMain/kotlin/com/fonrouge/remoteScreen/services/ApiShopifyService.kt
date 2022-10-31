package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.ContextDataUrl
import com.fonrouge.fsLib.model.ListContainer
import com.fonrouge.remoteScreen.model.SLocation
import com.fonrouge.remoteScreen.model.ShopifyProduct
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
import io.ktor.util.reflect.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.zip.CRC32

private fun String.toLine(): String {
    return this.replace("\n", "").trimIndent()
}

@OptIn(InternalAPI::class)
class ApiShopifyService {

    companion object {
        private const val apiVersion = "2022-10"
        const val baseURI = "https://casa-dulce-usa.myshopify.com/admin/api/${apiVersion}"
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
        var locations: Locations? = null
        var locationId: Long? = null

        init {
            runBlocking {
                locations = httpClient.get("$baseURI/locations.json").content.readUTF8Line()?.let {
                    Json.decodeFromString<Locations>(it)
                }
                locationId = locations?.locations?.get(0)?.id
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun taskGetItems(
        contextDataUrl: ContextDataUrl,
    ): ListContainer<ShopifyProduct> {
        val url = contextDataUrl.state ?: "$baseURI/products.json?limit=${contextDataUrl.tabSize}&query=CHAMOY"
        val response = httpClient.get(url)
        println("URL = $url")
        println("LINK = ${response.headers["link"]}")
        var products: Products? = null
        val crC32 = CRC32()
        response.content.read {
            val bytes = it.moveToByteArray()
            crC32.update(bytes)
            val s = String(bytes)
            products = Json.decodeFromString(s)
        }
        println("products: ${products?.products?.size}")
        return ListContainer(
            data = products?.products?.toList() ?: emptyList(),
            last_page = 1,
            checksum = crC32.value.toString(),
            state = response.headers["link"]
        )
    }

    @Serializable
    class Locations(
        val locations: Array<SLocation>
    )

    @Serializable
    class Products(
        val products: Array<ShopifyProduct>
    )
}
