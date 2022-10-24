package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.ContextDataUrl
import com.fonrouge.fsLib.model.IDataList
import com.fonrouge.fsLib.model.ListContainer
import com.fonrouge.remoteScreen.model.*
import io.kvision.annotations.KVService

@KVService
interface IDataListService : IDataList {
    suspend fun customerItm(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<CustomerItm>

    suspend fun customerOrderHdr(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<CustomerOrderHdr>

    suspend fun customerOrderItm(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<CustomerOrderItm>

    suspend fun inventoryItm(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<InventoryItm>

    suspend fun deliverList(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<DeliveryOrderItm>

    suspend fun deliveryHdr(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<DeliveryOrderHdr>

    suspend fun quickbooksItm(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<QuickbooksItm>

    suspend fun shopifyItm(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<ShopifyProduct>
}
