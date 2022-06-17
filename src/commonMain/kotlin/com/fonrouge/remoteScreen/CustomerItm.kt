package com.fonrouge.remoteScreen

import kotlin.js.JsExport

@kotlinx.serialization.Serializable
@JsExport
class CustomerItm(
    var _id: String,
    var company: String,
    var lastName: String,
    var firstName: String,
    var street: String,
    var city: String,
    var state: String,
    var zip: String,
    var phone1: String?,
    var email: String,
)
