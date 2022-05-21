package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.database.inventoryItmColl
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter

actual class InventoryItmService : IInventoryItmService {

    override suspend fun inventoryItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm> {
        val list = inventoryItmColl.find().toList()
        return RemoteData(list)
    }
}
