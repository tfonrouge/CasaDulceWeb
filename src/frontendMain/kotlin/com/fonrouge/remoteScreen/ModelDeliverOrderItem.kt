package com.fonrouge.remoteScreen

object ModelDeliverOrderItem {

    private val deliveryOrderItemService = DeliveryOrderItemService()

    suspend fun addDeliveryOrderItem(deliverOrderItem: ViewDeliverItem){
        deliveryOrderItemService.addDeliveryOrderItem(DeliveryOrderItm)
    }
    suspend fun deleteDeliveryOrderItem(_id: String): Boolean {
        return deliveryOrderItemService.deleteDeliveryOrderItem(_id)
    }
    suspend fun updateFieldQty(_id: String, value: Int){
        deliveryOrderItemService.updateFieldQty(_id, value)
    }
    suspend fun updateFieldSize(_id: String, value: String){
        deliveryOrderItemService.updateFieldSize(_id, value)
    }
}
