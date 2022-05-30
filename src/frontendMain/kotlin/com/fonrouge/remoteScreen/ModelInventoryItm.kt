package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.InventoryItmService

object ModelInventoryItm {

    private val inventoryItmService = InventoryItmService()

    suspend fun getInventoryItm(_id: String): InventoryItm {
        return inventoryItmService.getInventoryItm(_id)
    }
}
