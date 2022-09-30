package com.fonrouge.remoteScreen.services

import io.ktor.server.application.*

actual class CasaDulceService(private val call: ApplicationCall) : ICasaDulceService {
    override suspend fun getProfile() = call.withProfile { it }

    override suspend fun ping(hello: String): String {
        println("from frotend: $hello")
        val userProfile = call.withProfile { it }
        return "hello from server... ${userProfile.name}"
    }
}
