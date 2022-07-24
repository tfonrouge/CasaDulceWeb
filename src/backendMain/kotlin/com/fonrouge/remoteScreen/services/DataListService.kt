package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.CustomerOrderItm
import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.database.customerItmDb
import com.fonrouge.remoteScreen.database.customerOrderHdrDb
import com.fonrouge.remoteScreen.database.customerOrderItmDb
import com.fonrouge.remoteScreen.database.inventoryItmDb
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter

actual class DataListService : IDataListService {
    override suspend fun customerItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerItm> {
        val firstStage = customerItmDb.buildFirstStage(
            match = null,
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return customerItmDb.remoteData(firstStage = firstStage)
    }

    override suspend fun customerOrderHdr(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderHdr> {
        val firstStage = customerOrderHdrDb.buildFirstStage(
            match = null,
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return customerOrderHdrDb.remoteData(firstStage = firstStage)
    }

    override suspend fun customerOrderItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderItm> {
        val firstStage = customerOrderItmDb.buildFirstStage(
            match = null,
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return customerOrderItmDb.remoteData(firstStage = firstStage)
    }

    override suspend fun inventoryItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm> {
        val firstStage = inventoryItmDb.buildFirstStage(
            match = null,
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return inventoryItmDb.remoteData(firstStage = firstStage)
    }
}
