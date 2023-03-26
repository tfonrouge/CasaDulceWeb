package com.fonrouge.casaDulceWeb.services

import io.kvision.annotations.KVService
import io.kvision.remote.RemoteOption

@KVService
interface ISelectService {
    suspend fun customerItm(search: String?, initial: String?, state: String?): List<RemoteOption>
    suspend fun inventoryItm(search: String?, initial: String?, state: String?): List<RemoteOption>
}
