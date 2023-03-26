package com.fonrouge.casaDulceWeb.model

import com.fonrouge.casaDulceWeb.Security
import com.fonrouge.casaDulceWeb.User
import com.fonrouge.casaDulceWeb.services.UserService
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
