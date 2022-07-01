package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.DeliveryOrderItm
import io.kvision.annotations.KVService
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter

@KVService
interface IDeliveryService {

    suspend fun DeliveryOrderItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<DeliveryOrderItm>
}
