package com.fonrouge.casaDulceWeb.model

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.model.base.BaseDoc
import com.fonrouge.fsLib.serializers.OId
import kotlin.js.JsExport

@kotlinx.serialization.Serializable
@JsExport
@Collection("customerOrderItms")
class CustomerOrderItm(
    override var _id: OId<CustomerOrderItm> = OId(),
    var customerOrderHdr_id: OId<CustomerOrderHdr>,
    var inventoryItm_id: String,
    var qty: Int,
    var size: String,
) : BaseDoc<OId<CustomerOrderItm>> {
    var inventoryItm: InventoryItm? = null
}
