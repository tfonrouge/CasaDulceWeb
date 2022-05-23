package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.InventoryItm
import io.kvision.annotations.KVService
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter

@KVService
interface ICasaDulceService {
    suspend fun inventoryItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm>

    suspend fun customerItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerItm>
}
