package com.fonrouge.casaDulceWeb.services

import com.fonrouge.casaDulceWeb.User
import io.kvision.annotations.KVService

@KVService
interface IUserService {
    suspend fun getUser(): User
}
