package com.fonrouge.remoteScreen

object ModelDeliverItem {

    private val deliverItemService = DeliverItemService()

    suspend fun getDeliverItem(_id: String): DeliveryOrderItm {
        return deliverItemService.getDeliverItem(_id)
    }
}
