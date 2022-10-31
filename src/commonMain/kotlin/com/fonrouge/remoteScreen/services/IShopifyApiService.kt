package com.fonrouge.remoteScreen.services

import io.kvision.annotations.KVService

@KVService
interface IShopifyApiService {
    suspend fun getImageSrc(barcode: String): String
    suspend fun syncFromShopify(): Boolean
}