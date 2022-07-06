package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.DeliveryOrderItm
import com.fonrouge.remoteScreen.database.AggLookup
import com.fonrouge.remoteScreen.database.buildRemoteData
import com.fonrouge.remoteScreen.database.customerItmColl
import com.fonrouge.remoteScreen.database.deliveryOrderColl
import io.ktor.server.application.*
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import javax.inject.Inject

actual class DeliveryService : IDeliveryService {
    @Inject
    lateinit var call: ApplicationCall

    val aggLookup: AggLookup<DeliveryOrderItm, CustomerItm> = AggLookup(
        from = customerItmColl,
        localField = DeliveryOrderItm::customerOrderItm_id,
        foreignField = CustomerItm::_id,
        newAs = DeliveryOrderItm::customerItm
    )

    override suspend fun DeliveryOrderList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<DeliveryOrderItm> {
        return deliveryOrderColl.buildRemoteData(page, size, filter, sorter, aggLookup = aggLookup)
    }


}
