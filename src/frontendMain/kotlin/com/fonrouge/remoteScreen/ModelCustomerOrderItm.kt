package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CustomerOrderItmService

object ModelCustomerOrderItm {

    private val customerOrderItmService = CustomerOrderItmService()
    suspend fun addCustomerOrderItm(customerOrderItm: CustomerOrderItm) {
        customerOrderItmService.addCustomerOrderItm(customerOrderItm)
    }

    suspend fun deleteCustomerOrderItm(_id: String): Boolean {
        return customerOrderItmService.deleteCustomerOrderItm(_id)
    }

    suspend fun updateFieldQty(_id: String, value: Int) {
        customerOrderItmService.updateFieldQty(_id, value)
    }
    suspend fun updateFieldSize(_id: String, value: String){
        customerOrderItmService.updateFieldSize(_id, value)
    }

}
