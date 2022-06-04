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
}
