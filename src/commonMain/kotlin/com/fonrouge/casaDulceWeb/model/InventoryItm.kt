package com.fonrouge.casaDulceWeb.model

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.model.base.BaseDoc
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
    var departmentName: String?,
    var price: Double?,
    var wholesalePrice: Double?,
    var casePrice: Double?,
) : BaseDoc<String>
