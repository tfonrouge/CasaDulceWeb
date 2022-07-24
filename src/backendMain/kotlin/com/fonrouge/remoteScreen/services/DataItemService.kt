package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.model.CrudAction
import com.fonrouge.fsLib.model.CrudAction.*
import com.fonrouge.fsLib.model.ItemContainer
import com.fonrouge.fsLib.model.ItemContainerCallType
import com.fonrouge.fsLib.model.ItemContainerCallType.Action
import com.fonrouge.fsLib.model.ItemContainerCallType.Query
import com.fonrouge.remoteScreen.database.customerOrderHdrDb
import com.fonrouge.remoteScreen.database.customerOrderItmDb
import com.fonrouge.remoteScreen.database.getNextNumId
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.match

actual class DataItemService : IDataItemService {
    override suspend fun customerOrderHdr(
        crudAction: CrudAction,
        _id: String?,
        customerOrderHdr: CustomerOrderHdr?,
        itemContainerCallType: ItemContainerCallType
    ): ItemContainer<CustomerOrderHdr> {
        return when (itemContainerCallType) {
            Query -> when (crudAction) {
                Create -> ItemContainer(result = true)
                Read, Update -> ItemContainer(
                    item = customerOrderHdrDb.getItem(match = match(CustomerOrderHdr::_id eq _id))
                )
                Delete -> ItemContainer(result = true)
            }
            Action -> when (crudAction) {
                Create -> {
                    customerOrderHdr?._id = ObjectId().toHexString()
                    customerOrderHdr?.numId = getNextNumId(customerOrderHdrDb)
                    customerOrderHdrDb.insertOne(customerOrderHdr)
                }
                Update -> customerOrderHdrDb.updateOne(_id = _id, item = customerOrderHdr)
                Delete -> {
                    var result = customerOrderItmDb.collection.deleteMany(CustomerOrderItm::customerOrderHdr_id eq _id)
                    if (result.wasAcknowledged()) {
                        result = customerOrderHdrDb.collection.deleteOne(CustomerOrderHdr::_id eq _id)
                    }
                    ItemContainer(result = result.deletedCount == 1L)
                }
                else -> ItemContainer(result = false, description = "Unknown error...")
            }
        }
    }

    override suspend fun customerOrderItm(
        crudAction: CrudAction,
        _id: String?,
        customerOrderItm: CustomerOrderItm?,
        itemContainerCallType: ItemContainerCallType
    ): ItemContainer<CustomerOrderItm> {
        TODO("Not yet implemented")
    }

    override suspend fun inventoryItm(
        crudAction: CrudAction,
        _id: String?,
        inventoryItm: InventoryItm?,
        itemContainerCallType: ItemContainerCallType
    ): ItemContainer<InventoryItm> {
        TODO("Not yet implemented")
    }
}
