package com.fonrouge.remoteScreen.database

import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.InventoryItm
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import org.bson.Document
import org.litote.kmongo.coroutine.CoroutineCollection

val inventoryItmColl by lazy {
    mongoDatabase.getCollection<InventoryItm>(collectionName = "inventoryItms")
}

val customerItmColl by lazy {
    mongoDatabase.getCollection<CustomerItm>(collectionName = "customerItms")
}

suspend inline fun <reified T : Any> CoroutineCollection<T>.buildRemoteData(
    page: Int?,
    size: Int?,
    filter: List<RemoteFilter>?,
    sorter: List<RemoteSorter>?,
    state: String?
): RemoteData<T> {

    val nPage = page ?: 1
    val nSize = size ?: 10
    val nSkip = nSize * (nPage - 1)

    val pipeline = mutableListOf<Document>()

    val filterValue = Document()

    if (filter != null && filter.isNotEmpty()) {
        filter.forEach { remoteFilter ->
            val value = when (remoteFilter.type) {
                "like" -> Document("\$regex", remoteFilter.value).append("\$options", "i")
                else -> remoteFilter.value
            }
            filterValue.append(remoteFilter.field, value)
        }
    }
    pipeline.add(Document("\$match", filterValue))
    pipeline.add(Document("\$skip", nSkip))
    pipeline.add(Document("\$limit", nSize))

    val count = this.countDocuments(filterValue)

    val list = aggregate<T>(pipeline = pipeline).toList()

    return RemoteData(data = list, last_page = (count / nSize + 1).toInt())
}
