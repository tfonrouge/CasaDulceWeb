package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.InventoryItm
import io.kvision.annotations.KVService
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter

@KVService
interface IInventoryItmService {
    suspend fun inventoryItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm>

    suspend fun createProductWith(inventoryItm: InventoryItm)

    suspend fun updateProduct(inventoryItm: InventoryItm, fieldName: String)
}