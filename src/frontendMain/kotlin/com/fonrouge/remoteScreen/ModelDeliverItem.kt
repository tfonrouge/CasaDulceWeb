package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.DeliverItemService

object ModelDeliverItem {

    private val deliverItemService = DeliverItemService()

    suspend fun getDeliverItem(_id: String): DeliveryOrderItm {
        return deliverItemService.getDeliverItem(_id)
    }
}
