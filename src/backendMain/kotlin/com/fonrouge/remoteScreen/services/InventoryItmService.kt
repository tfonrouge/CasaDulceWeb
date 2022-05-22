package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.database.inventoryItmColl
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import org.bson.Document

actual class InventoryItmService : IInventoryItmService {

    override suspend fun inventoryItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm> {
        println("ILI, page=$page, size=$size, filter=$filter, sorter=$sorter, state=$state")
        val nPage = page ?: 1
        val nSize = size ?: 10
        val nSkip = nSize * (nPage - 1)

        val pipeline = mutableListOf<Document>()

        if (filter != null && filter.isNotEmpty()) {
            val filterDoc = Document()
            filter.forEach { remoteFilter ->
                val value = when (remoteFilter.type) {
                    "like" -> Document("\$regex", remoteFilter.value).append("\$options", "i")
                    else -> remoteFilter.value
                }
                filterDoc.append(remoteFilter.field, value)
            }
            pipeline.add(Document("\$match", filterDoc))
        }
        pipeline.add(Document("\$skip", nSkip))
        pipeline.add(Document("\$limit", nSize))

        val list = inventoryItmColl.aggregate<InventoryItm>(
            pipeline = pipeline
        ).toList()
        return RemoteData(data = list, last_page = nPage + 1)
    }
}
