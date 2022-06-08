package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerOrderHdr
import io.kvision.annotations.KVService
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter

@KVService
interface ICustomerOrderHdrService {

    suspend fun customerOrderHdrItem(_id: String): CustomerOrderHdr

    suspend fun customerOrderHdrList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderHdr>

    suspend fun createNewCustomerOrderHdr(): CustomerOrderHdr

    suspend fun updateCustomerOrderHdr(customerOrderHdr: CustomerOrderHdr): Boolean

    suspend fun deleteCustomerOrderHdr(_id: String) : Boolean
}
