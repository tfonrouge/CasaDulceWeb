package com.fonrouge.remoteScreen.upload

import com.fonrouge.remoteScreen.CatalogType
import com.fonrouge.remoteScreen.database.CustomerItmDb
import com.fonrouge.remoteScreen.database.InventoryItmDb
import com.fonrouge.remoteScreen.model.CustomerItm
import com.fonrouge.remoteScreen.model.InventoryItm
import com.fonrouge.remoteScreen.uploadsDir
import com.mongodb.client.model.UpdateOneModel
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
import org.apache.poi.ss.usermodel.CellType.*
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.bson.Document
import org.litote.kmongo.coroutine.CoroutineCollection
import java.io.File
import java.io.FileInputStream

fun Route.uploadsRoute() {

    var fileDescription = ""
    var fileName = ""

    route("/kv/upload") {

        post("{catalog}") {
            val catalogType = call.parameters["catalog"]?.let {
                CatalogType.valueOf(it)
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
                when (catalogType) {
                    CatalogType.Products -> importProducts(InventoryItmDb.coroutineColl, inventoryItmMap, fileName)
                    CatalogType.Customers -> importProducts(CustomerItmDb.coroutineColl, customerItmMap, fileName)
                }
                buildJsonObject { put("response", "$fileDescription and imported ok") }
            } catch (e: Exception) {
                buildJsonObject { put("error", "${e.message}") }
            }
            call.respondText(result.toString())
        }
    }
}

private enum class CellType {
    CtInt,
    CtLong,
    CtDouble,
    CtString,
}

private class ColumnMap(
    val fieldname: String,
    val colName: String,
    val cellType: CellType
)

private val inventoryItmMap = listOf(
    ColumnMap(InventoryItm::_id.name, "Item Number", CellType.CtString),
    ColumnMap(InventoryItm::name.name, "Item Name", CellType.CtString),
    ColumnMap(InventoryItm::size.name, "Size", CellType.CtString),
    ColumnMap(InventoryItm::upc.name, "UPC", CellType.CtString),
    ColumnMap(InventoryItm::departmentName.name, "Department Name", CellType.CtString),
    ColumnMap(InventoryItm::price.name, "Regular Price", CellType.CtInt),
    ColumnMap(InventoryItm::wprice.name, "Wholesale Price", CellType.CtInt),
    ColumnMap(InventoryItm::cprice.name, "Case Price", CellType.CtInt),
)

private val customerItmMap = listOf(
    ColumnMap(CustomerItm::_id.name, "Customer Reward ID", CellType.CtString),
    ColumnMap(CustomerItm::company.name, "Company", CellType.CtString),
    ColumnMap(CustomerItm::lastName.name, "Last Name", CellType.CtString),
    ColumnMap(CustomerItm::firstName.name, "First Name", CellType.CtString),
    ColumnMap(CustomerItm::street.name, "Street", CellType.CtString),
    ColumnMap(CustomerItm::city.name, "City", CellType.CtString),
    ColumnMap(CustomerItm::state.name, "State", CellType.CtString),
    ColumnMap(CustomerItm::zip.name, "ZIP", CellType.CtString),
    ColumnMap(CustomerItm::phone1.name, "Phone1", CellType.CtString),
    ColumnMap(CustomerItm::email.name, "EMail", CellType.CtString),
)

private suspend fun <T : Any> importProducts(
    collection: CoroutineCollection<T>,
    columnMapList: List<ColumnMap>,
    fileName: String
) {
    val inputStream = withContext(Dispatchers.IO) {
        FileInputStream(fileName)
    }
    val workbook = WorkbookFactory.create(inputStream)
    val pairLinkList = mutableListOf<Pair<ColumnMap, Int>>()
    val buffer = mutableListOf<UpdateOneModel<T>>()
    val bufferLimit = 500
    workbook.getSheetAt(0).rowIterator().forEach { row ->
        if (pairLinkList.size == 0) {
            getPairLinkList(columnMapList, row, pairLinkList)
        } else {
            val doc = Document()
            var _id: Any? = null
            pairLinkList.forEach { productsColValuesIntPair ->
                val cell: Cell? = row.getCell(productsColValuesIntPair.second)
                val value = try {
                    when (productsColValuesIntPair.first.cellType) {
                        CellType.CtInt -> {
                            if (cell?.cellType == NUMERIC) {
                                cell.numericCellValue.toInt()
                            } else {
                                0
                            }
                        }

                        CellType.CtLong -> {
                            if (cell?.cellType == NUMERIC) {
                                cell.numericCellValue.toLong()
                            } else {
                                0L
                            }
                        }

                        CellType.CtDouble -> {
                            if (cell?.cellType == NUMERIC) {
                                cell.numericCellValue
                            } else {
                                0.0
                            }
                        }

                        CellType.CtString -> {
                            when (cell?.cellType) {
                                _NONE -> ""
                                NUMERIC -> cell.numericCellValue.toLong().toString()
                                STRING -> cell.stringCellValue
                                FORMULA -> cell.stringCellValue
                                BLANK -> ""
                                BOOLEAN -> "${cell.booleanCellValue}"
                                ERROR -> "error"
                                else -> ""
                            }
                        }
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
            buffer.add(UpdateOneModel(Document("_id", _id), Document("\$set", doc), UpdateOptions().upsert(true)))
            if (buffer.size > bufferLimit) {
                val r = collection.bulkWrite(buffer)
                println("bulkwrite $r")
                buffer.clear()
            }
        }
    }
    if (buffer.size > 0) {
        val r = collection.bulkWrite(buffer)
        println("bulkwrite $r")
        buffer.clear()
    }
}

private fun getPairLinkList(
    columnMapList: List<ColumnMap>,
    row: Row,
    pairLinkList: MutableList<Pair<ColumnMap, Int>>
) {
    columnMapList.forEach { pairProduct ->
        row.cellIterator().forEach { cell ->
            if (pairProduct.colName.equals(other = cell.stringCellValue, ignoreCase = true)) {
                pairLinkList.add(pairProduct to cell.columnIndex)
            }
        }
    }
}
