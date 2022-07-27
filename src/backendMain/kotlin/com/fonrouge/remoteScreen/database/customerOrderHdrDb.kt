package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import com.fonrouge.fsLib.mongoDb.LookupBuilder
import com.fonrouge.fsLib.mongoDb.mongoDbCollection
import com.fonrouge.remoteScreen.model.CustomerItm
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.DocumentWithNumId
import com.mongodb.client.model.IndexOptions
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.eq

val customerOrderHdrDb = mongoDbCollection<CustomerOrderHdr>(
    lookupBuilderList = listOf(
        LookupBuilder(
            cTableDb = customerItmDb,
            localField = CustomerOrderHdr::customerItm_id,
            foreignField = CustomerItm::_id,
            resultProperty = CustomerOrderHdr::customerItm
        )
    )
) {
    runBlocking {
        collection.ensureUniqueIndex(
            properties = arrayOf(CustomerOrderHdr::numId)
        )
    }
}

suspend fun getNextNumId(cTableDb: CTableDb<out DocumentWithNumId<*>>): Int {
    return cTableDb.collection.find()
        .descendingSort(DocumentWithNumId<*>::numId)
        .limit(1)
        .first()?.let { it.numId + 1 } ?: 1
}
