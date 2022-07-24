package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.model.base.BaseModel
import kotlin.js.JsExport

@kotlinx.serialization.Serializable
@JsExport
@com.fonrouge.fsLib.Collection("customerOrderItms")
class CustomerOrderItm(
    override var _id: String,
    var customerOrderHdr_id: String,
    var inventoryItm_id: String,
    var qty: Int,
    var size: String,
) : BaseModel<String> {
    var inventoryItm: InventoryItm? = null
}
