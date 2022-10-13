package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.model.IDataItem
import com.fonrouge.fsLib.model.ItemContainer
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import io.kvision.annotations.KVService

@KVService
interface IDataItemService : IDataItem {
    suspend fun customerOrderHdr(
        _id: String?,
        state: StateItem<CustomerOrderHdr>,
    ): ItemContainer<CustomerOrderHdr>

    suspend fun customerOrderItm(
        _id: String?,
        state: StateItem<CustomerOrderItm>,
    ): ItemContainer<CustomerOrderItm>

    suspend fun inventoryItm(
        _id: String?,
        state: StateItem<InventoryItm>,
    ): ItemContainer<InventoryItm>

    suspend fun inventoryItmByUpc(upc: String): ItemContainer<InventoryItm>

    suspend fun priceCheck(
        _id: String?,
        state: StateItem<InventoryItm>,
    ): ItemContainer<InventoryItm>
}
