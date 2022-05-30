package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.UserProfile
import io.kvision.annotations.KVService

@KVService
interface ICasaDulceService {

    suspend fun getProfile(): UserProfile

    suspend fun ping(hello: String): String
}
