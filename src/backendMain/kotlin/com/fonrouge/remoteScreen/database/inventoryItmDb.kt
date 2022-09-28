package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import com.fonrouge.fsLib.mongoDb.collation
import com.fonrouge.remoteScreen.model.InventoryItm
import com.mongodb.client.model.IndexOptions
import kotlinx.coroutines.runBlocking

val InventoryItmDb = object : CTableDb<InventoryItm, String>(
    klass = InventoryItm::class
) {
    init {
        runBlocking {
            coroutineColl.ensureIndex(InventoryItm::name, indexOptions = IndexOptions().collation(collation()))
            coroutineColl.ensureIndex(InventoryItm::upc, indexOptions = IndexOptions().collation(collation()))
        }
    }
}
