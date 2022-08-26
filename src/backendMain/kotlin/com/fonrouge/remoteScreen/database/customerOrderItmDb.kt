package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import com.fonrouge.fsLib.mongoDb.LookupBuilder
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import com.mongodb.client.model.IndexOptions
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.eq

val CustomerOrderItmDb = object : CTableDb<CustomerOrderItm, String>(
    klass = CustomerOrderItm::class
) {
    override val lookupFun: (() -> List<LookupBuilder<CustomerOrderItm, *, *, *>>) = {
        listOf(
            LookupBuilder(
                cTableDb = InventoryItmDb::class,
                localField = CustomerOrderItm::inventoryItm_id,
                foreignField = InventoryItm::_id,
                resultProperty = CustomerOrderItm::inventoryItm
            )
        )
    }

    init {
        runBlocking {
            coroutineColl.ensureUniqueIndex(
                properties = arrayOf(CustomerOrderItm::customerOrderHdr_id, CustomerOrderItm::inventoryItm_id),
                indexOptions = IndexOptions().partialFilterExpression(CustomerOrderItm::inventoryItm_id eq null)
            )
        }
    }
}
