package com.fonrouge.remoteScreen.database

import com.fonrouge.remoteScreen.InventoryItm

val inventoryItmColl by lazy {
    mongoDatabase.getCollection<InventoryItm>(collectionName = "inventoryItms")
}
