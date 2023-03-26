package com.fonrouge.casaDulceWeb.services

import io.kvision.annotations.KVService

@KVService
interface IShopifyApiService {
    suspend fun getImageSrc(barcode: String): String
    suspend fun syncFromShopifyApi(): Boolean
}