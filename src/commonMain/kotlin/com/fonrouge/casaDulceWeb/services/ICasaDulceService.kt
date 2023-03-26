package com.fonrouge.casaDulceWeb.services

import com.fonrouge.casaDulceWeb.User
import io.kvision.annotations.KVService

@KVService
interface ICasaDulceService {

    suspend fun getProfile(): User

    suspend fun ping(hello: String): String
}
