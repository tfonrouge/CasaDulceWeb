package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.User
import io.kvision.annotations.KVService

@KVService
interface IUserService {
    suspend fun getUser(): User
}
