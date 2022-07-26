package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.model.IDataList
import com.fonrouge.remoteScreen.model.CustomerItm
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import io.kvision.annotations.KVService
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter

@KVService
interface IDataListService : IDataList {
    suspend fun customerItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerItm>

    suspend fun customerOrderHdr(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderHdr>

    suspend fun customerOrderItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderItm>

    suspend fun customerOrderItmByCustomerOrder(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderItm>

    suspend fun inventoryItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm>
}
