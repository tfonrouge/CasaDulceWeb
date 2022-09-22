package com.fonrouge.remoteScreen.model

import com.fonrouge.remoteScreen.Security
import com.fonrouge.remoteScreen.User
import com.fonrouge.remoteScreen.services.UserService
import io.kvision.state.ObservableValue

object Model {
    private val profileService = UserService()

    val user = ObservableValue(User())

    suspend fun readProfile() {
        Security.withAuth {
            user.value = profileService.getUser()
        }
    }
}
