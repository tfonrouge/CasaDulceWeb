package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.StateItem.CallType.Action
import com.fonrouge.fsLib.StateItem.CallType.Query
import com.fonrouge.fsLib.localDateTimeNow
import com.fonrouge.fsLib.model.CrudAction.*
import com.fonrouge.fsLib.model.ItemContainer
import com.fonrouge.fsLib.mongoDb.ModelLookup
import com.fonrouge.remoteScreen.database.customerOrderHdrDb
import com.fonrouge.remoteScreen.database.customerOrderItmDb
import com.fonrouge.remoteScreen.database.getNextNumId
import com.fonrouge.remoteScreen.database.inventoryItmDb
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
                Create -> {
                    state.item = CustomerOrderHdr(
                        _id = ObjectId().toHexString(),
                        numId = getNextNumId(customerOrderHdrDb),
                        customerItm_id = "",
                        created = localDateTimeNow(),
                        status = "$"
                    )
                    customerOrderHdrDb.insertOne(state = state)
                }

                Read, Update -> customerOrderHdrDb.getItemContainer(_id = _id)
                Delete -> ItemContainer(result = true)
            }

            Action -> when (state.crudAction) {
                Create -> {
                    state.item?.numId = getNextNumId(customerOrderHdrDb)
                    customerOrderHdrDb.insertOne(state = state)
                }

                Update -> customerOrderHdrDb.updateOne(_id = _id, state = state)
                Delete -> {
                    var result = customerOrderItmDb.collection.deleteMany(CustomerOrderItm::customerOrderHdr_id eq _id)
                    if (result.wasAcknowledged()) {
                        result = customerOrderHdrDb.collection.deleteOne(CustomerOrderHdr::_id eq _id)
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
                    customerOrderItmDb.insertOne(state = state)
                }

                Read, Update -> customerOrderItmDb.getItemContainer(
                    _id = _id,
                    modelLookupList = listOf(
                        ModelLookup(resultProperty = CustomerOrderHdr::customerItm)
                    )
                )

                Delete -> TODO()
            }

            Action -> when (state.crudAction) {
                Create -> customerOrderItmDb.insertOne(state)
                Update -> customerOrderItmDb.updateOne(_id = _id, state)
                Delete -> customerOrderItmDb.deleteOneById(_id = _id)
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
                Read -> inventoryItmDb.getItemContainer(_id)
                else -> ItemContainer(result = false)
            }

            Action -> ItemContainer(result = false)
        }
    }
}
