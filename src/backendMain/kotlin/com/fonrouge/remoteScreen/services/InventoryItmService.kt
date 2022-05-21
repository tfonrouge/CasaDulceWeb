package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.database.inventoryItmColl
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import io.kvision.remote.ServiceException
import org.bson.Document
import org.litote.kmongo.newId

actual class InventoryItmService : IInventoryItmService {

    override suspend fun inventoryItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm> {
        val list = inventoryItmColl.find().toList()
        return RemoteData(list)
    }

    override suspend fun createProductWith(inventoryItm: InventoryItm) {
        try {
            inventoryItm.id = newId<String>().toString()
            inventoryItmColl.insertOne(inventoryItm)
        } catch (e: ServiceException) {
            println("error on product create = ${e.message}")
            throw ServiceException("error on product create = ${e.message}")
        }
    }

    override suspend fun updateProduct(inventoryItm: InventoryItm, fieldName: String) {
        try {
            inventoryItmColl.updateOne(Document("_id", inventoryItm.id), inventoryItm)
        } catch (e: ServiceException) {
            println("error on product create = ${e.message}")
            throw ServiceException("error on product create = ${e.message}")
        }
    }
}
