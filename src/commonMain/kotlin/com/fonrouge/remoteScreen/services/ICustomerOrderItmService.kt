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

    suspend fun addCustomerOrderItm(customerOrderItm: CustomerOrderItm): Boolean
    suspend fun deleteCustomerOrderItm(_id: String): Boolean
    suspend fun updateFieldQty(_id: String, value: Int): Boolean
    suspend fun updateFieldSize(_id: String, value: String)

}
