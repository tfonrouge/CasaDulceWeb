package com.fonrouge.remoteScreen.model

import com.fonrouge.remoteScreen.Security
import com.fonrouge.remoteScreen.UserProfile
import com.fonrouge.remoteScreen.services.UserProfileService
import io.kvision.state.ObservableValue

object Model {
    private val profileService = UserProfileService()

    val userProfile = ObservableValue(UserProfile())

    suspend fun readProfile() {
        Security.withAuth {
            userProfile.value = profileService.getProfile()
        }
    }
}
