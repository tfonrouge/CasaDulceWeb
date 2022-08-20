package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.StateItem.CallType.Action
import com.fonrouge.fsLib.StateItem.CallType.Query
import com.fonrouge.fsLib.localDateTimeNow
import com.fonrouge.fsLib.model.CrudAction.*
import com.fonrouge.fsLib.model.ItemContainer
import com.fonrouge.fsLib.mongoDb.ModelLookup
import com.fonrouge.remoteScreen.database.CustomerOrderHdrDb
import com.fonrouge.remoteScreen.database.CustomerOrderItmDb
import com.fonrouge.remoteScreen.database.InventoryItmDb
import com.fonrouge.remoteScreen.database.getNextNumId
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import org.bson.types.ObjectId
import org.litote.kmongo.eq

actual class DataItemService : IDataItemService {
    override suspend fun customerOrderHdr(
        _id: String?,
        state: StateItem<CustomerOrderHdr>,
    ): ItemContainer<CustomerOrderHdr> {
        return when (state.callType) {
            Query -> when (state.crudAction) {
                Create -> ItemContainer(result = true)
                Read, Update -> CustomerOrderHdrDb.getItemContainer(_id = _id)
                Delete -> ItemContainer(result = true)
            }

            Action -> when (state.crudAction) {
                Create -> {
                    state.item?.numId = getNextNumId(CustomerOrderHdrDb)
                    CustomerOrderHdrDb.insertOne(state = state)
                }

                Update -> CustomerOrderHdrDb.updateOne(_id = _id, state = state)
                Delete -> {
                    var result = CustomerOrderItmDb.collection.deleteMany(CustomerOrderItm::customerOrderHdr_id eq _id)
                    if (result.wasAcknowledged()) {
                        result = CustomerOrderHdrDb.collection.deleteOne(CustomerOrderHdr::_id eq _id)
                    }
                    ItemContainer(result = result.deletedCount == 1L)
                }

                else -> ItemContainer(result = false)
            }
        }
    }

    override suspend fun customerOrderItm(
        _id: String?,
        state: StateItem<CustomerOrderItm>,
    ): ItemContainer<CustomerOrderItm> {
        return when (state.callType) {
            Query -> when (state.crudAction) {
                Create -> {
                    state.item = CustomerOrderItm(
                        _id = ObjectId().toHexString(),
                        customerOrderHdr_id = state.contextDataUrl?.contextId ?: "",
                        inventoryItm_id = "",
                        qty = 1,
                        size = ""
                    )
                    CustomerOrderItmDb.insertOne(state = state)
                }

                Read, Update -> CustomerOrderItmDb.getItemContainer(
                    _id = _id,
                    ModelLookup(resultProperty = CustomerOrderHdr::customerItm)
                )

                Delete -> TODO()
            }

            Action -> when (state.crudAction) {
                Create -> CustomerOrderItmDb.insertOne(state)
                Update -> CustomerOrderItmDb.updateOne(_id = _id, state)
                Delete -> CustomerOrderItmDb.deleteOneById(_id = _id)
                else -> ItemContainer(result = false)
            }
        }
    }

    override suspend fun inventoryItm(
        _id: String?,
        state: StateItem<InventoryItm>,
    ): ItemContainer<InventoryItm> {
        return when (state.callType) {
            Query -> when (state.crudAction) {
                Create -> ItemContainer(result = false, description = "Not implemented ...")
                Read, Update -> InventoryItmDb.getItemContainer(_id)
                Delete -> ItemContainer(result = false, description = "Not allowed ...")
            }

            Action -> when(state.crudAction) {
                Create -> TODO()
                Read -> TODO()
                Update -> InventoryItmDb.updateOne(_id, state)
                Delete -> TODO()
            }
        }
    }

    override suspend fun priceCheck(_id: String?, state: StateItem<InventoryItm>): ItemContainer<InventoryItm> {
        TODO("Not yet implemented")
    }
}
