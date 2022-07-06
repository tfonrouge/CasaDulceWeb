package com.fonrouge.remoteScreen.database

import com.fonrouge.remoteScreen.*
import com.mongodb.client.model.IndexOptions
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import kotlinx.coroutines.runBlocking
import org.bson.Document
import org.bson.conversions.Bson
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.litote.kmongo.match

val inventoryItmColl: CoroutineCollection<InventoryItm> by lazy {
    mongoDatabase.getCollection(collectionName = "inventoryItms")
}

val customerItmColl by lazy {
    mongoDatabase.getCollection<CustomerItm>(collectionName = "customerItms")
}

val customerOrderHdrColl by lazy {
    mongoDatabase.getCollection<CustomerOrderHdr>(collectionName = "customerOrderHdrs").also {
        runBlocking {
            it.ensureUniqueIndex(
                properties = arrayOf(CustomerOrderHdr::userProfile, CustomerOrderHdr::status),
                indexOptions = IndexOptions().partialFilterExpression(CustomerOrderHdr::status eq "$")
            )
        }
    }
}

val customerOrderItmColl by lazy {
    mongoDatabase.getCollection<CustomerOrderItm>(collectionName = "customerOrderItms").also {
        runBlocking {
            it.ensureUniqueIndex(
                properties = arrayOf(CustomerOrderItm::customerOrderHdr_id, CustomerOrderItm::inventoryItm_id),
                indexOptions = IndexOptions().partialFilterExpression(CustomerOrderItm::inventoryItm_id eq null)
            )
        }
    }
}

val deliveryOrderColl by lazy {
    mongoDatabase.getCollection<DeliveryOrderItm>(collectionName = "deliveryOrders")
}

val userItmColl by lazy {
    mongoDatabase.getCollection<UserItm>(collectionName = "userItms")
}

suspend inline fun <reified T : IBase<*>> CoroutineCollection<T>.aggItem(
    _id: Any,
    aggLookup: AggLookup<*, *>? = null
): T? {
    val pipeline = mutableListOf(
        match(Document(IBase<*>::_id.name, _id))
    )
    aggLookup?.addToPipeline(pipeline)
    return aggregate<T>(pipeline).first()
}

suspend inline fun <reified T : Any> CoroutineCollection<T>.buildRemoteData(
    page: Int?,
    size: Int?,
    filter: List<RemoteFilter>?,
    sorter: List<RemoteSorter>?,
    match: Bson? = null,
    aggLookup: AggLookup<*, *>? = null,
): RemoteData<T> {

    val nPage = page ?: 1
    val nSize = size ?: 10
    val nSkip = nSize * (nPage - 1)

    val pipeline = mutableListOf<Bson>()

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

    match?.let {
        pipeline.add(match)
    }
    pipeline.add(Document("\$match", filterValue))
    aggLookup?.addToPipeline(pipeline)
    pipeline.add(Document("\$skip", nSkip))
    pipeline.add(Document("\$limit", nSize))

    val count = this.countDocuments(filterValue)

    val list = aggregate<T>(pipeline = pipeline).toList()

    return RemoteData(data = list, last_page = (count / nSize + 1).toInt())
}
