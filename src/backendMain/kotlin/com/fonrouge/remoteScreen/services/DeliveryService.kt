package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.DeliveryOrderItm
import com.fonrouge.remoteScreen.database.AggLookup
import com.fonrouge.remoteScreen.deliveryStatusList
import io.ktor.server.application.*
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import javax.inject.Inject

actual class DeliveryService : IDeliveryService {
    @Inject
    lateinit var call: ApplicationCall

    val aggLookup: AggLookup<DeliveryOrderItm, DeliveryOrderItm> = AggLookup(
        from = DeliveryOrderItm::customerOrderItm,
        localField = DeliveryOrderItm::customerOrderItm_id,
        foreignField = DeliveryOrderItm::customerOrderItm
    )
    override suspend fun DeliveryOrderItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<DeliveryOrderItm> {
        TODO("Not yet implemented")
    }


}
