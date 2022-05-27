package com.fonrouge.remoteScreen

@kotlinx.serialization.Serializable
class CustomerOrderItm(
    val _id: String,
    val inventoryItm: InventoryItm,
    val qty: Int,
    val unit: String,
)
