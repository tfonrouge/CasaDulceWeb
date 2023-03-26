package com.fonrouge.casaDulceWeb.services

import io.kvision.annotations.KVService

@KVService
interface ITypeaheadService {
    suspend fun inventoryItem(search: String?, state: String?): List<String>
}