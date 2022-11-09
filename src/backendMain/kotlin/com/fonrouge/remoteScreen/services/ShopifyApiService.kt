package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.model.ListContainer
import com.fonrouge.remoteScreen.database.Tables.ShopifyImageDb
import com.fonrouge.remoteScreen.database.Tables.ShopifyProductDb
import com.fonrouge.remoteScreen.database.Tables.ShopifyVariantDb
import com.fonrouge.remoteScreen.model.ShopifyLocation
import com.fonrouge.remoteScreen.model.ShopifyImage
import com.fonrouge.remoteScreen.model.ShopifyProduct
import com.fonrouge.remoteScreen.model.ShopifyVariant
import com.mongodb.client.model.ReplaceOneModel
import com.mongodb.client.model.ReplaceOptions
import com.mongodb.client.model.WriteModel
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.litote.kmongo.eq
import java.time.Duration
import java.util.zip.CRC32

@OptIn(InternalAPI::class)
actual class ShopifyApiService : IShopifyApiService {

    companion object {
        var taskRetrievingShopifyProducts = false
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

    override suspend fun getImageSrc(barcode: String): String {
        return ShopifyVariantDb.coroutineColl.findOne(ShopifyVariant::barcode eq barcode)?.let { shopifyVariant ->
            ShopifyProductDb.coroutineColl.findOne(ShopifyProduct::_id eq shopifyVariant.product_id)?.let {
                it.image?.src
            }
        } ?: ""
    }

    override suspend fun syncFromShopify(): Boolean {
        if (!taskRetrievingShopifyProducts) {
            taskRetrievingShopifyProducts = true
            var state: String? = null
            println(">>>>>>>>>>>>>>>> start")
            while (true) {
                val listContainer = taskGetItems(state)
                listContainer.state?.let { s ->
                    val a = s.split(',')
                    state = a.find { it.contains("rel=\"next\"") }?.let { getUrl(it) }
                }
                checkList(listContainer.data)
                state ?: break
            }
            println(">>>>>>>>>>>>>>>> end")
            delay(Duration.ofSeconds(30).toMillis())
            println(">>>>>>>>>>>>>>>> delay end")
            taskRetrievingShopifyProducts = false
            return true
        }
        return false
    }

    private suspend fun checkList(shopifyProducts: List<ShopifyProduct>) {
        runBlocking {
            val shopifyProductWriteModels = mutableListOf<WriteModel<ShopifyProduct>>()
            val shopifyVariantWriteModels = mutableListOf<WriteModel<ShopifyVariant>>()
            val shopifyImageWriteModels = mutableListOf<WriteModel<ShopifyImage>>()
            shopifyProducts.forEach { shopifyProduct ->
                shopifyProduct.variants.forEach { shopifyVariant ->
                    shopifyVariantWriteModels.add(
                        ReplaceOneModel(
                            ShopifyVariant::_id eq shopifyVariant._id,
                            shopifyVariant,
                            ReplaceOptions().upsert(true)
                        )
                    )
                }
                shopifyProduct.variants = emptyArray()
                shopifyProduct.images.forEach { shopifyImage ->
                    shopifyImageWriteModels.add(
                        ReplaceOneModel(
                            ShopifyImage::_id eq shopifyImage._id,
                            shopifyImage,
                            ReplaceOptions().upsert(true)
                        )
                    )
                }
                shopifyProduct.images = emptyArray()
                shopifyProductWriteModels.add(
                    ReplaceOneModel(
                        ShopifyProduct::_id eq shopifyProduct._id,
                        shopifyProduct,
                        ReplaceOptions().upsert(true)
                    )
                )
            }
            if (shopifyProductWriteModels.isNotEmpty()) ShopifyProductDb.coroutineColl.bulkWrite(
                shopifyProductWriteModels
            )
            if (shopifyVariantWriteModels.isNotEmpty()) ShopifyVariantDb.coroutineColl.bulkWrite(
                shopifyVariantWriteModels
            )
            if (shopifyImageWriteModels.isNotEmpty()) ShopifyImageDb.coroutineColl.bulkWrite(shopifyImageWriteModels)
        }
    }

    private suspend fun taskGetItems(state: String?): ListContainer<ShopifyProduct> {
        val pageSize = 250
        val url = state ?: "$baseURI/products.json?limit=$pageSize"
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

    private fun getUrl(s: String): String {
        return s.split(';')[0].trim().let { it.substring(1, it.length - 1) }
    }

    @Serializable
    class Locations(
        val locations: Array<ShopifyLocation>
    )

    @Serializable
    class Products(
        val products: Array<ShopifyProduct>
    )
}
