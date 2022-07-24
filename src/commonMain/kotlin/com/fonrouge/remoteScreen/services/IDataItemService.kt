package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.model.CrudAction
import com.fonrouge.fsLib.model.IDataItem
import com.fonrouge.fsLib.model.ItemContainer
import com.fonrouge.fsLib.model.ItemContainerCallType
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import io.kvision.annotations.KVService

@KVService
interface IDataItemService : IDataItem {
    suspend fun customerOrderHdr(
        crudAction: CrudAction,
        _id: String?,
        customerOrderHdr: CustomerOrderHdr?,
        itemContainerCallType: ItemContainerCallType,
    ): ItemContainer<CustomerOrderHdr>

    suspend fun customerOrderItm(
        crudAction: CrudAction,
        _id: String?,
        customerOrderItm: CustomerOrderItm?,
        itemContainerCallType: ItemContainerCallType,
    ): ItemContainer<CustomerOrderItm>

    suspend fun inventoryItm(
        crudAction: CrudAction,
        _id: String?,
        inventoryItm: InventoryItm?,
        itemContainerCallType: ItemContainerCallType,
    ): ItemContainer<InventoryItm>
}
