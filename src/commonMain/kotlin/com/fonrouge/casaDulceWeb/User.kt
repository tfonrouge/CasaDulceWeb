package com.fonrouge.casaDulceWeb

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val name: String? = null,
    val username: String? = null,
    val password: String? = null,
    val password2: String? = null,
)

var user: User? = null
