package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.LookupBuilder
import com.fonrouge.fsLib.mongoDb.mongoDbCollection
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import com.mongodb.client.model.IndexOptions
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.eq

val customerOrderItmDb = mongoDbCollection(
    lookupBuilderList = listOf(
        LookupBuilder(
            cTableDb = inventoryItmDb,
            localField = CustomerOrderItm::inventoryItm_id,
            foreignField = InventoryItm::_id,
            resultProperty = CustomerOrderItm::inventoryItm
        )
    )
) {
    runBlocking {
        collection.ensureUniqueIndex(
            properties = arrayOf(CustomerOrderItm::customerOrderHdr_id, CustomerOrderItm::inventoryItm_id),
            indexOptions = IndexOptions().partialFilterExpression(CustomerOrderItm::inventoryItm_id eq null)
        )
    }
}
