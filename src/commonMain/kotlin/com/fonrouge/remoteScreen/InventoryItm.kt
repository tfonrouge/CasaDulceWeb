package com.fonrouge.remoteScreen

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class InventoryItm(
    @Contextual
    @SerialName("_id")
    var id: String? = null,
    var code: String,
    var description: String,
    var unit: String,
)
