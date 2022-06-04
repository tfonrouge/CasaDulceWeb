package com.fonrouge.remoteScreen

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
class InventoryItm(
    var _id: String,
    var name: String,
    var size: String,
    var upc: String,
    var departmentName: String
)
