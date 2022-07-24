package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.UserProfile
import io.kvision.annotations.KVService

@KVService
interface IUserProfileService {
    suspend fun getProfile(): UserProfile
}
