package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.User
import com.fonrouge.remoteScreen.user
import com.google.inject.Inject
import io.ktor.server.application.*
import io.ktor.server.sessions.*

actual class UserService : IUserService {
    @Inject
    lateinit var call: ApplicationCall

    override suspend fun getUser() = call.withProfile { it }
}

suspend fun <RESP> ApplicationCall.withProfile(block: suspend (User) -> RESP): RESP {
    user = this.sessions.get<User>()
    return user?.let {
        block(it)
    } ?: throw IllegalStateException("User Profile not set!")
}
