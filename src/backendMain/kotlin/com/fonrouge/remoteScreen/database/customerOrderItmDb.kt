package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import com.fonrouge.fsLib.mongoDb.lookupField
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import kotlinx.coroutines.runBlocking

val CustomerOrderItmDb = object : CTableDb<CustomerOrderItm, String>(
    klass = CustomerOrderItm::class
) {
    override val lookupFun = {
        listOf(
            lookupField(
                cTableDb = InventoryItmDb::class,
                localField = CustomerOrderItm::inventoryItm_id,
                foreignField = InventoryItm::_id,
                resultField = CustomerOrderItm::inventoryItm
            )
        )
    }

    init {
        runBlocking {
            coroutineColl.ensureUniqueIndex(
                properties = arrayOf(CustomerOrderItm::customerOrderHdr_id, CustomerOrderItm::inventoryItm_id),
            )
        }
    }
}
