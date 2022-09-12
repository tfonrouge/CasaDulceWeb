package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import com.fonrouge.fsLib.mongoDb.LookupBuilder
import com.fonrouge.fsLib.mongoDb.localToForeign
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import kotlinx.coroutines.runBlocking

val CustomerOrderItmDb = object : CTableDb<CustomerOrderItm, String>(
    klass = CustomerOrderItm::class
) {
    override val lookupFun: (() -> List<LookupBuilder<CustomerOrderItm, *, *, *>>) = {
        listOf(
            LookupBuilder(
                cTableDb = InventoryItmDb::class,
                localToForeign = CustomerOrderItm::inventoryItm_id localToForeign InventoryItm::_id,
                resultProperty = CustomerOrderItm::inventoryItm
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
