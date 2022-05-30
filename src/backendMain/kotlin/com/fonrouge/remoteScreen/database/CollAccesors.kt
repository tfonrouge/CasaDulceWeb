package com.fonrouge.remoteScreen.database

import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.CustomerOrderHdr
import com.fonrouge.remoteScreen.CustomerOrderItm
import com.fonrouge.remoteScreen.InventoryItm
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.UnwindOptions
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import kotlinx.coroutines.runBlocking
import org.bson.Document
import org.bson.conversions.Bson
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.litote.kmongo.lookup
import org.litote.kmongo.unwind
import kotlin.reflect.KProperty1

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
                properties = arrayOf(CustomerOrderItm::customerOrderHdr_id, CustomerOrderItm::inventoryItm),
                indexOptions = IndexOptions().partialFilterExpression(CustomerOrderItm::inventoryItm eq null)
            )
        }
    }
}

val userItmColl by lazy {
    mongoDatabase.getCollection<UserItm>(collectionName = "userItms")
}

class AggLookup<S : Any, T : Any>(
    val from: CoroutineCollection<T>,
    val localField: KProperty1<S, Any?>,
    val foreignField: KProperty1<T, *>,
    val newAs: KProperty1<S, T?>,
)

suspend inline fun <reified T : Any> CoroutineCollection<T>.buildRemoteData(
    page: Int?,
    size: Int?,
    filter: List<RemoteFilter>?,
    sorter: List<RemoteSorter>?,
    state: String?,
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

    pipeline.add(Document("\$match", filterValue))
    aggLookup?.let {
        pipeline.add(
            lookup(
                from = aggLookup.from.collection.namespace.collectionName,
                localField = aggLookup.localField.name,
                foreignField = aggLookup.foreignField.name,
                newAs = aggLookup.newAs.name
            )
        )
        pipeline.add(
            unwind(
                fieldName = "\$${aggLookup.newAs.name}",
                unwindOptions = UnwindOptions().preserveNullAndEmptyArrays(true)
            )
        )
    }
    pipeline.add(Document("\$skip", nSkip))
    pipeline.add(Document("\$limit", nSize))

    val count = this.countDocuments(filterValue)

    val list = aggregate<T>(pipeline = pipeline).toList()

    return RemoteData(data = list, last_page = (count / nSize + 1).toInt())
}
