package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.StateItem
import com.fonrouge.fsLib.StateItem.CallType.Action
import com.fonrouge.fsLib.StateItem.CallType.Query
import com.fonrouge.fsLib.model.CrudAction.*
import com.fonrouge.fsLib.model.ItemResponse
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
    ): ItemResponse<CustomerOrderHdr> {
        return when (state.callType) {
            Query -> when (state.crudAction) {
                Create -> {
                    val documentoNuevo = CustomerOrderHdrDb.coroutineColl.findOne(
                        filter = CustomerOrderHdr::status eq "$"
                    )
                    if (documentoNuevo == null) {
                        CustomerOrderHdrDb.insertOne(state.copy(item = CustomerOrderHdr().also {
                            it.numId = getNextNumId(CustomerOrderHdrDb)
                        }))
                    } else {
                        ItemResponse(isOk = false, msgError = "Existe previamente documento Nuevo ...")
                    }
                }

                Read, Update, Delete -> CustomerOrderHdrDb.itemResponse(_id = _id)
            }

            Action -> when (state.crudAction) {
                Create, Update -> CustomerOrderHdrDb.updateOneById(_id = _id, state = state)
                Delete -> {
                    var result =
                        CustomerOrderItmDb.coroutineColl.deleteMany(CustomerOrderItm::customerOrderHdr_id eq _id)
                    if (result.wasAcknowledged()) {
                        result = CustomerOrderHdrDb.coroutineColl.deleteOne(CustomerOrderHdr::_id eq _id)
                        ItemResponse(isOk = result.deletedCount == 1L)
                    } else {
                        ItemResponse(isOk = false, msgError = "Error en operacion requerida ...")
                    }
                }

                else -> ItemResponse(isOk = false)
            }
        }
    }

    override suspend fun customerOrderItm(
        _id: String?,
        state: StateItem<CustomerOrderItm>,
    ): ItemResponse<CustomerOrderItm> {
        return when (state.callType) {
            Query -> when (state.crudAction) {
                Create -> {
                    CustomerOrderItmDb.insertOne(
                        state = state.copy(
                            item = CustomerOrderItm(
                                _id = ObjectId().toHexString(),
                                customerOrderHdr_id = state.contextId(),
                                inventoryItm_id = "",
                                qty = 1,
                                size = ""
                            )
                        )
                    )
                }

                Read, Update, Delete -> CustomerOrderItmDb.itemResponse(
                    _id = _id,
                    ModelLookup(resultProperty = CustomerOrderHdr::customerItm)
                )
            }

            Action -> when (state.crudAction) {
                Create, Update -> CustomerOrderItmDb.updateOneById(_id = _id, state)
                Delete -> CustomerOrderItmDb.deleteOneById(_id = _id)
                else -> ItemResponse(isOk = false)
            }
        }
    }

    override suspend fun inventoryItm(
        _id: String?,
        state: StateItem<InventoryItm>,
    ): ItemResponse<InventoryItm> {
        return when (state.callType) {
            Query -> when (state.crudAction) {
                Create -> ItemResponse(isOk = false, msgError = "Not allowed ...")
                Read -> InventoryItmDb.itemResponse(_id)
                Update -> ItemResponse(isOk = false, msgError = "No se permite editar ... ")
                //InventoryItmDb.getItemResponse(_id)
                Delete -> ItemResponse(isOk = false, msgError = "Not allowed ...")
            }

            Action -> when (state.crudAction) {
                Create -> TODO()
                Read -> TODO()
                Update -> InventoryItmDb.updateOneById(_id, state)
                Delete -> TODO()
            }
        }
    }

    override suspend fun inventoryItmByUpc(upc: String): ItemResponse<InventoryItm> {
        return ItemResponse(item = InventoryItmDb.findOne(InventoryItm::upc eq upc))
    }

    override suspend fun priceCheck(_id: String?, state: StateItem<InventoryItm>): ItemResponse<InventoryItm> {
        TODO("Not yet implemented")
    }
}
