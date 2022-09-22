package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CasaDulceService
import io.kvision.state.ObservableValue

object ModelCasaDulce {

    private val casaDulceService = CasaDulceService()

    val obsProfile = ObservableValue(User())

    suspend fun ping(hello: String): String {
        return Security.withAuth {
            casaDulceService.ping(hello)
        }
    }

    suspend fun readProfile() {
        obsProfile.value = casaDulceService.getProfile()
    }
}
