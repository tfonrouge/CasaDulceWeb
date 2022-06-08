package com.fonrouge.remoteScreen

import kotlin.js.JsExport

@kotlinx.serialization.Serializable
@JsExport
class CustomerItm(
    val _id: String,
    val company: String,
    val lastName: String,
    val firstName: String,
    val street: String,
    val city: String,
    val state: String,
    val zip: String,
    val phone1: String?,
    val email: String,
)
