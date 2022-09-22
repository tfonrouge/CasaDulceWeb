package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.StateItem.CallType.Action
import com.fonrouge.fsLib.StateItem.CallType.Query
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
                Create -> {
                    state.item = CustomerOrderHdr().also {
                        it.numId = getNextNumId(CustomerOrderHdrDb)
                    }
                    val documentoNuevo = CustomerOrderHdrDb.coroutineColl.findOne(
                        filter = CustomerOrderHdr::status eq "$"
                    )
                    if (documentoNuevo == null) {
                        CustomerOrderHdrDb.insertOne(state)
                    } else {
                        ItemContainer(isOk = false, msgError = "Existe previamente documento Nuevo ...")
                    }
                }

                Read, Update, Delete -> CustomerOrderHdrDb.getItemContainer(_id = _id)
            }

            Action -> when (state.crudAction) {
                Create, Update -> CustomerOrderHdrDb.updateOne(_id = _id, state = state)
                Delete -> {
                    var result =
                        CustomerOrderItmDb.coroutineColl.deleteMany(CustomerOrderItm::customerOrderHdr_id eq _id)
                    if (result.wasAcknowledged()) {
                        result = CustomerOrderHdrDb.coroutineColl.deleteOne(CustomerOrderHdr::_id eq _id)
                        ItemContainer(isOk = result.deletedCount == 1L)
                    } else {
                        ItemContainer(isOk = false, msgError = "Error en operacion requerida ...")
                    }
                }

                else -> ItemContainer(isOk = false)
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
                        customerOrderHdr_id = state.contextId(),
                        inventoryItm_id = "",
                        qty = 1,
                        size = ""
                    )
                    CustomerOrderItmDb.insertOne(state = state)
                }

                Read, Update, Delete -> CustomerOrderItmDb.getItemContainer(
                    _id = _id,
                    ModelLookup(resultProperty = CustomerOrderHdr::customerItm)
                )
            }

            Action -> when (state.crudAction) {
                Create, Update -> CustomerOrderItmDb.updateOne(_id = _id, state)
                Delete -> CustomerOrderItmDb.deleteOneById(_id = _id)
                else -> ItemContainer(isOk = false)
            }
        }
    }

    override suspend fun inventoryItm(
        _id: String?,
        state: StateItem<InventoryItm>,
    ): ItemContainer<InventoryItm> {
        return when (state.callType) {
            Query -> when (state.crudAction) {
                Create -> ItemContainer(isOk = false, msgError = "Not allowed ...")
                Read -> InventoryItmDb.getItemContainer(_id)
                Update -> ItemContainer(isOk = false, msgError = "No se permite editar ... ")
                //InventoryItmDb.getItemContainer(_id)
                Delete -> ItemContainer(isOk = false, msgError = "Not allowed ...")
            }

            Action -> when (state.crudAction) {
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
