package com.fonrouge.remoteScreen

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
class InventoryItm(
//    @SerialName("_id")
    var _id: String,
    var code: String,
    var description: String,
    var unit: String,
    var stock: Int,
)
