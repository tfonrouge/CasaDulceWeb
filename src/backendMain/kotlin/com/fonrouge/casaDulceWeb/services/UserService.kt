package com.fonrouge.casaDulceWeb.services

import com.fonrouge.casaDulceWeb.User
import com.fonrouge.casaDulceWeb.user
import io.ktor.server.application.*
import io.ktor.server.sessions.*

actual class UserService(private val call: ApplicationCall) : IUserService {
    override suspend fun getUser() = call.withProfile { it }
}

suspend fun <RESP> ApplicationCall.withProfile(block: suspend (User) -> RESP): RESP {
    user = this.sessions.get<User>()
    return user?.let {
        block(it)
    } ?: throw IllegalStateException("User Profile not set!")
}
