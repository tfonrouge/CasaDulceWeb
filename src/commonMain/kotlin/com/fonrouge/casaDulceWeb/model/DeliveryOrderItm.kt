package com.fonrouge.casaDulceWeb.model

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.model.base.BaseDoc
import com.fonrouge.fsLib.serializers.OId
import kotlin.js.JsExport

@kotlinx.serialization.Serializable
@JsExport
@Collection("deliveryOrderItms")
class DeliveryOrderItm(
    override var _id: OId<DeliveryOrderItm> = OId(),
    var customerOrderHdr_id: String = "",
    var inventoryItm_id: String,
    var qty: Int,
    var size: String,
) : BaseDoc<OId<DeliveryOrderItm>> {
    var inventoryItm: InventoryItm? = null
}
