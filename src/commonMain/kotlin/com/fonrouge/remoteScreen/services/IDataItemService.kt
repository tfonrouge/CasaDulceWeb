package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.StateFunctionItem
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
        customerOrderHdr: CustomerOrderHdr?,
        state: StateFunctionItem,
    ): ItemContainer<CustomerOrderHdr>

    suspend fun customerOrderItm(
        _id: String?,
        customerOrderItm: CustomerOrderItm?,
        state: StateFunctionItem,
    ): ItemContainer<CustomerOrderItm>

    suspend fun inventoryItm(
        _id: String?,
        inventoryItm: InventoryItm?,
        state: StateFunctionItem,
    ): ItemContainer<InventoryItm>
}
