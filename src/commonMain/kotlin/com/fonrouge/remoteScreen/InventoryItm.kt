package com.fonrouge.remoteScreen

import kotlinx.serialization.Serializable

@Serializable
class InventoryItm(
    var _id: Int,
    var itemName: String,
    var size: String,
    var upc: String,
    var department: String
)
