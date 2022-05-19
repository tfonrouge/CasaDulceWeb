package com.fonrouge.remoteScreen.database

import com.fonrouge.remoteScreen.InventoryItm
import org.litote.kmongo.coroutine.coroutine

val inventoryItmColl by lazy {
    mongoDatabase.getCollection("invItems", InventoryItm::class.java).coroutine
}
