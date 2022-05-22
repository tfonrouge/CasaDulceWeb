package com.fonrouge.remoteScreen.upload

import com.fonrouge.remoteScreen.uploadsDir
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.File

private enum class UploadCatalogType {
    products,
    customers,
}

fun Route.uploadsRoute() {

    var fileDescription = ""
    var fileName = ""

    route("/kv/upload") {

        post("{catalog}") {
            val uploadCatalogType = call.parameters["catalog"]?.let {
                UploadCatalogType.valueOf(it)
            } ?: return@post

            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        fileDescription = part.value
                    }
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()
                        File("$uploadsDir/$fileName").writeBytes(fileBytes)
                    }
                    is PartData.BinaryItem -> TODO()
                    is PartData.BinaryChannelItem -> TODO()
                }
            }

            val result = try {
                when (uploadCatalogType) {
                    UploadCatalogType.products -> importProducts()
                    UploadCatalogType.customers -> importCustomers()
                }
                buildJsonObject { put("response", "$fileDescription and imported ok") }
            } catch (e: Exception) {
                buildJsonObject { put("error", "${e.message}") }
            }
            call.respondText(result.toString())
        }
    }
}

fun importCustomers() {

}

fun importProducts() {

}
