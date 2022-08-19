package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.CTableDb
import com.fonrouge.remoteScreen.model.InventoryItm

val InventoryItmDb = object : CTableDb<InventoryItm, String>(
    klass = InventoryItm::class
) {}
