package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.ContextDataUrl
import com.fonrouge.fsLib.model.ListContainer
import com.fonrouge.fsLib.mongoDb.ModelLookup
import com.fonrouge.remoteScreen.database.*
import com.fonrouge.remoteScreen.model.*
import org.litote.kmongo.eq
import org.litote.kmongo.match

actual class DataListService : IDataListService {
    override suspend fun customerItm(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<CustomerItm> {
        return CustomerItmDb.listContainer(
            contextDataUrl = contextDataUrl
        )
    }

    override suspend fun customerOrderHdr(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<CustomerOrderHdr> {
        return CustomerOrderHdrDb.listContainer(
            contextDataUrl = contextDataUrl,
            modelLookup = arrayOf(ModelLookup(resultProperty = CustomerOrderHdr::customerItm))
        )
    }

    override suspend fun customerOrderItm(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<CustomerOrderItm> {
        return CustomerOrderItmDb.listContainer(
            match = match(CustomerOrderItm::customerOrderHdr_id eq contextDataUrl?.contextIdValue()),
            contextDataUrl = contextDataUrl,
            modelLookup = arrayOf(ModelLookup(resultProperty = CustomerOrderItm::inventoryItm))
        )
    }

    override suspend fun inventoryItm(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<InventoryItm> {
        return InventoryItmDb.listContainer(
            contextDataUrl = contextDataUrl
        )
    }

    override suspend fun deliverList(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<DeliveryOrderItm> {
        TODO("Not yet implemented")
    }

    override suspend fun deliveryHdr(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<DeliveryOrderHdr> {
        return DeliveryOrderHdrDb.listContainer(
            contextDataUrl = contextDataUrl,
            modelLookup = arrayOf(ModelLookup(resultProperty = DeliveryOrderHdr::customerItm))
        )
    }

    override suspend fun quickbooksItm(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<QuickbooksItm> {
        TODO("Not yet implemented")
    }

    override suspend fun shopifyItm(
        contextDataUrl: ContextDataUrl?
    ): ListContainer<ShopifyProduct> {
        return ApiShopifyService().taskGetItems(
            page = contextDataUrl?.tabPage,
            size = contextDataUrl?.tabSize
        )
    }
}
