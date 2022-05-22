package com.fonrouge.remoteScreen.upload

import com.fonrouge.remoteScreen.database.inventoryItmColl
import com.fonrouge.remoteScreen.uploadsDir
import com.mongodb.client.model.UpdateOptions
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.bson.Document
import java.io.File
import java.io.FileInputStream

private enum class UploadCatalogType {
    Products,
    Customers,
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
                        fileName = "$uploadsDir/${part.originalFileName as String}"
                        val fileBytes = part.streamProvider().readBytes()
                        File(fileName).writeBytes(fileBytes)
                    }
                    is PartData.BinaryItem -> TODO()
                    is PartData.BinaryChannelItem -> TODO()
                }
            }

            val result = try {
                when (uploadCatalogType) {
                    UploadCatalogType.Products -> importProducts(fileName)
                    UploadCatalogType.Customers -> importCustomers(fileName)
                }
                buildJsonObject { put("response", "$fileDescription and imported ok") }
            } catch (e: Exception) {
                buildJsonObject { put("error", "${e.message}") }
            }
            call.respondText(result.toString())
        }
    }
}

fun importCustomers(fileName: String) {
    val inputStream = FileInputStream(fileName)
    val workbook = WorkbookFactory.create(inputStream)
    workbook.getSheetAt(0).rowIterator().forEach { row ->
        println(row)
    }
}

private enum class CellType {
    CtInt,
    CtString,
}

private class ProductsColValues(
    val fieldname: String,
    val colName: String,
    val cellType: CellType
)

private val pairProducts = listOf(
    ProductsColValues("_id", "Item Number", CellType.CtInt),
    ProductsColValues("name", "Item Name", CellType.CtString),
    ProductsColValues("size", "Size", CellType.CtString),
    ProductsColValues("upc", "UPC", CellType.CtString),
    ProductsColValues("departmentName", "Department Name", CellType.CtString),
)

suspend fun importProducts(fileName: String) {
    val inputStream = withContext(Dispatchers.IO) {
        FileInputStream(fileName)
    }
    val workbook = WorkbookFactory.create(inputStream)
    val pairLinkList = mutableListOf<Pair<ProductsColValues, Int>>()
    workbook.getSheetAt(0).rowIterator().forEach { row ->
        if (pairLinkList.size == 0) {
            getPairLinkList(row, pairLinkList)
        } else {
            println("row = ${row.rowNum}")
            val doc = Document()
            var _id: Any? = null
            pairLinkList.forEach { productsColValuesIntPair ->
                val cell: Cell? = row.getCell(productsColValuesIntPair.second)
                val value = try {
                    when (productsColValuesIntPair.first.cellType) {
                        CellType.CtInt -> cell?.numericCellValue?.toInt()?: 0
                        CellType.CtString -> cell?.stringCellValue ?: ""
                    }
                } catch (e: Exception) {
                    println(e.message)
                }
                if (productsColValuesIntPair.first.fieldname == "_id") {
                    _id = value
                } else {
                    doc.append(productsColValuesIntPair.first.fieldname, value)
                }
            }
            val r = inventoryItmColl.updateOne(
                filter = Document("_id", _id),
                update = Document("\$set", doc),
                options = UpdateOptions().upsert(true)
            )
            println(r)
        }
    }

}

private fun getPairLinkList(row: Row, pairLinkList: MutableList<Pair<ProductsColValues, Int>>) {
    pairProducts.forEach { pairProduct ->
        row.cellIterator().forEach { cell ->
            if (pairProduct.colName.equals(other = cell.stringCellValue, ignoreCase = true)) {
                pairLinkList.add(pairProduct to cell.columnIndex)
            }
        }
    }
}
