package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerItm
import io.kvision.annotations.KVService
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteOption
import io.kvision.remote.RemoteSorter

@KVService
interface ICustomerItmService {

    suspend fun customerItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerItm>

    suspend fun selectCustomerItm(
        search: String?,
        initial: String?,
        state: String?
    ): List<RemoteOption>
}
