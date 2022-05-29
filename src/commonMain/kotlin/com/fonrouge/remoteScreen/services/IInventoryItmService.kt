package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.InventoryItm
import io.kvision.annotations.KVService
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteOption
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

    suspend fun selectInventoryItm(
        search: String?,
        initial: String?,
        state: String?
    ): List<RemoteOption>

    suspend fun getInventoryItm(_id: Int?): InventoryItm
}
