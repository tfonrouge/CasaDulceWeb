package com.fonrouge.remoteScreen

@kotlinx.serialization.Serializable
class CustomerOrderItm(
    var _id: String,
    var customerOrderHdr_id: String,
    var inventoryItm_id: String,
    val qty: Int,
    val size: String,
) {
    var inventoryItm: InventoryItm? = null
}
