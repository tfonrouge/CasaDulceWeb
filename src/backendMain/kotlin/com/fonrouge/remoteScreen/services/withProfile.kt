package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.UserProfile
import io.ktor.server.application.*
import io.ktor.server.sessions.*

suspend fun <RESP> ApplicationCall.withProfile(block: suspend (UserProfile) -> RESP): RESP {
    val profile = this.sessions.get<UserProfile>()
    return profile?.let {
        block(profile)
    } ?: throw IllegalStateException("User Profile not set!")
}
