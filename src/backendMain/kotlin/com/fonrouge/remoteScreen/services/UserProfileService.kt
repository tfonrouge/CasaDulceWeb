package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.UserProfile
import com.google.inject.Inject
import io.ktor.server.application.*
import io.ktor.server.sessions.*

actual class UserProfileService : IUserProfileService {
    @Inject
    lateinit var call: ApplicationCall

    override suspend fun getProfile() = call.withProfile { it }
}

suspend fun <RESP> ApplicationCall.withProfile(block: suspend (UserProfile) -> RESP): RESP {
    val userProfile = this.sessions.get<UserProfile>()
    return userProfile?.let {
        block(userProfile)
    } ?: throw IllegalStateException("User Profile not set!")
}
