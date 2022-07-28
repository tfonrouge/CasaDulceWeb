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
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.bson
import org.litote.kmongo.eq
import org.litote.kmongo.match

actual class DataItemService : IDataItemService {
    override suspend fun customerOrderHdr(
        _id: String?,
        state: StateItem<CustomerOrderHdr>,
    ): ItemContainer<CustomerOrderHdr> {
        val itemContainer: ItemContainer<CustomerOrderHdr> = when (state.callType) {
            Query -> when (state.crudAction) {
                Create -> {
                    customerOrderHdrDb.insertOne(
                        item = CustomerOrderHdr(
                            _id = ObjectId().toHexString(),
                            numId = getNextNumId(customerOrderHdrDb),
                            customerItm_id = "",
                            created = localDateTimeNow(),
                            status = "$"
                        )
                    )
                }

                Read, Update -> ItemContainer(
                    item = customerOrderHdrDb.getItem(match = match(CustomerOrderHdr::_id eq _id))
                )

                Delete -> ItemContainer(result = true)
            }

            Action -> when (state.crudAction) {
                Create -> {
//                    state.item?._id = ObjectId().toHexString()
                    state.item?.numId = getNextNumId(customerOrderHdrDb)
                    customerOrderHdrDb.insertOne(state.item)
                }

                Update -> customerOrderHdrDb.updateOne(_id = _id, item = state.item)
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
        return itemContainer
    }

    override suspend fun customerOrderItm(
        _id: String?,
        state: StateItem<CustomerOrderItm>,
    ): ItemContainer<CustomerOrderItm> {
        val itemContainer: ItemContainer<CustomerOrderItm>? = when (state.callType) {
            Query -> when (state.crudAction) {
                Create -> customerOrderItmDb.insertOne(
                    item = CustomerOrderItm(
                        _id = ObjectId().toHexString(),
                        customerOrderHdr_id = state.contextDataUrl?.contextId ?: "",
                        inventoryItm_id = "",
                        qty = 1,
                        size = ""
                    )
                )

                Read, Update -> ItemContainer(
                    item = customerOrderItmDb.getItem(
                        match = match(CustomerOrderItm::_id eq _id),
                        modelLookupList = listOf(
                            ModelLookup(
                                resultProperty = CustomerOrderHdr::customerItm
                            )
                        )
                    )
                )

                Delete -> TODO()
            }

            Action -> when (state.crudAction) {
                Create -> {
                    customerOrderItmDb.insertOne(state.item)
                }

                Read -> TODO()
                Update -> {
                    if (state.item != null) {
                        customerOrderItmDb.updateOne(_id = _id, state.item)
                    } else
                        state.json?.bson?.let { bson ->
                            customerOrderItmDb.updateOne(_id = _id, Document("\$set", bson))
                        }
                }

                Delete -> TODO()
            }
        }
        return itemContainer ?: ItemContainer(result = false)
    }

    override suspend fun inventoryItm(
        _id: String?,
        state: StateItem<InventoryItm>,
    ): ItemContainer<InventoryItm> {
        TODO("Not yet implemented")
    }
}
