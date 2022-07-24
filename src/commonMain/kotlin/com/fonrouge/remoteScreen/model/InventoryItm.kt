package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.model.base.BaseModel
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
@Collection("inventoryItms")
class InventoryItm(
    override var _id: String,
    var name: String,
    var size: String,
    var upc: String,
    var departmentName: String
) : BaseModel<String>
