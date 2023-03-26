package com.fonrouge.casaDulceWeb.model

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.model.base.BaseDoc
import kotlin.js.JsExport

@kotlinx.serialization.Serializable
@JsExport
@Collection("customerItms")
class CustomerItm(
    override var _id: String,
    var company: String,
    var lastName: String,
    var firstName: String,
    var street: String,
    var city: String,
    var state: String,
    var zip: String,
    var phone1: String?,
    var email: String,
) : BaseDoc<String>
