package com.fonrouge.remoteScreen

@kotlinx.serialization.Serializable
class CustomerItm(
    val _id: Long,
    val company: String,
    val lastName: String,
    val firstName: String,
    val street: String,
    val city: String,
    val state:String,
    val zip: String,
    val phone1: String?,
    val email: String,
)
