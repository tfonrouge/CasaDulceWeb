package com.fonrouge.casaDulceWeb.services

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.model.IDataItem
import com.fonrouge.fsLib.model.ItemResponse
import com.fonrouge.fsLib.serializers.OId
import com.fonrouge.casaDulceWeb.model.CustomerOrderHdr
import com.fonrouge.casaDulceWeb.model.CustomerOrderItm
import com.fonrouge.casaDulceWeb.model.InventoryItm
import io.kvision.annotations.KVService

@KVService
interface IDataItemService : IDataItem {
    suspend fun customerOrderHdr(
        _id: OId<CustomerOrderHdr>?,
        state: StateItem<CustomerOrderHdr>,
    ): ItemResponse<CustomerOrderHdr>

    suspend fun customerOrderItm(
        _id: OId<CustomerOrderItm>?,
        state: StateItem<CustomerOrderItm>,
    ): ItemResponse<CustomerOrderItm>

    suspend fun inventoryItm(
        _id: String?,
        state: StateItem<InventoryItm>,
    ): ItemResponse<InventoryItm>

    suspend fun inventoryItmByUpc(upc: String): ItemResponse<InventoryItm>

    suspend fun priceCheck(
        _id: String?,
        state: StateItem<InventoryItm>,
    ): ItemResponse<InventoryItm>
}
