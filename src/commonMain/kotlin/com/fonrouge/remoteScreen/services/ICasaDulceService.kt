package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.User
import io.kvision.annotations.KVService

@KVService
interface ICasaDulceService {

    suspend fun getProfile(): User

    suspend fun ping(hello: String): String
}
