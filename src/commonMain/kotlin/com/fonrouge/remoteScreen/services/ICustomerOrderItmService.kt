package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerOrderItm
import io.kvision.annotations.KVService
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter

@KVService
interface ICustomerOrderItmService {

    suspend fun customerOrderItmByHdrList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderItm>

    suspend fun addCustomerOrderItm(customerOrderItm: CustomerOrderItm) : Boolean
}
