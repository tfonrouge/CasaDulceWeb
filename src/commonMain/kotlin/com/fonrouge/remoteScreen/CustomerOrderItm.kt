package com.fonrouge.remoteScreen

@kotlinx.serialization.Serializable
class CustomerOrderItm(
    var _id: String,
    var customerOrderHdr_id: String,
    val inventoryItm: InventoryItm?,
    val qty: Int,
    val size: String,
) {
    var inventoryItm_id = inventoryItm?._id?.toString() ?: ""
}
