package com.fonrouge.remoteScreen

@kotlinx.serialization.Serializable
class Customer(
    val id: Int,
    val name: String,
    val address: String,
    val phone1: String,
    val email: String,
)
