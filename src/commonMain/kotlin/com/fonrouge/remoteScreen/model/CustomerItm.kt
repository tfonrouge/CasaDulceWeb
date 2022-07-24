package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.model.base.BaseModel
import kotlin.js.JsExport

@kotlinx.serialization.Serializable
@JsExport
@com.fonrouge.fsLib.Collection("customerItms")
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
) : BaseModel<String>
