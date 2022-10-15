package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.annotations.MongoDoc
import com.fonrouge.fsLib.model.base.BaseModel
import com.fonrouge.fsLib.newObjectId
import kotlin.js.JsExport

@kotlinx.serialization.Serializable
@JsExport
@MongoDoc("deliveryOrderItms")
class DeliveryOrderItm(
    override var _id: String = newObjectId(),
    var customerOrderHdr_id: String = "",
    var inventoryItm_id: String,
    var qty: Int,
    var size: String,
) : BaseModel<String> {
    var inventoryItm: InventoryItm? = null
}
