package com.fonrouge.remoteScreen

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class InventoryItm(
    @SerialName("_id")
    var id: String,
    var code: String,
    var description: String,
    var unit: String,
    @SerialName("stock")
    var qty: Int,
)
