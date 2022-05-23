package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.database.buildRemoteData
import com.fonrouge.remoteScreen.database.customerItmColl
import com.fonrouge.remoteScreen.database.inventoryItmColl
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter

actual class CasaDulceService : ICasaDulceService {

    override suspend fun inventoryItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm> {
        return inventoryItmColl.buildRemoteData(page, size, filter, sorter, state)
    }

    override suspend fun customerItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerItm> {
        return customerItmColl.buildRemoteData(page, size, filter, sorter, state)
    }
}
