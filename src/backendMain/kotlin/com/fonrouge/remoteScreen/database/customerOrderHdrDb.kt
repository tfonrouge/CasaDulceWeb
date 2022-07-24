package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.LookupBuilder
import com.fonrouge.fsLib.mongoDb.mongoDbCollection
import com.fonrouge.remoteScreen.model.CustomerItm
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
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
            properties = arrayOf(CustomerOrderHdr::userProfile, CustomerOrderHdr::status),
            indexOptions = IndexOptions().partialFilterExpression(CustomerOrderHdr::status eq "$")
        )
    }
}

fun a1() {
//    FSObjectIdSerializer.serialize()
}
