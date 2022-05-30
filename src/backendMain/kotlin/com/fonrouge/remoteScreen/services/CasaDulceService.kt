package com.fonrouge.remoteScreen.services

import com.google.inject.Inject
import io.ktor.server.application.*

actual class CasaDulceService : ICasaDulceService {

    @Inject
    lateinit var call: ApplicationCall

    override suspend fun getProfile() = call.withProfile { it }

    override suspend fun ping(hello: String): String {
        println("from frotend: $hello")
        val userProfile = call.withProfile { it }
        return "hello from server... ${userProfile.name}"
    }
}
