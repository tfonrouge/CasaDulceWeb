package com.fonrouge.remoteScreen.services

import io.kvision.annotations.KVService

@KVService
interface ITypeaheadService {
    suspend fun inventoryItem(search: String?, state: String?): List<String>
}