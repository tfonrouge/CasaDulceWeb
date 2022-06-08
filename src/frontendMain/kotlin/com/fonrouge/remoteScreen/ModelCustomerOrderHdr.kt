package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CustomerOrderHdrService

object ModelCustomerOrderHdr {

    private val customerOrderHdrService = CustomerOrderHdrService()

    suspend fun customerOrderHdrItem(_id: String): CustomerOrderHdr {
        return customerOrderHdrService.customerOrderHdrItem(_id)
    }

    suspend fun createNewCustomerOrderHdr(): CustomerOrderHdr {
        return Security.withAuth {
            customerOrderHdrService.createNewCustomerOrderHdr()
        }
    }

    suspend fun updateCustomerOrderHdr(customerOrderHdr: CustomerOrderHdr): Boolean {
        return Security.withAuth {
            customerOrderHdrService.updateCustomerOrderHdr(customerOrderHdr)
        }
    }

    suspend fun deleteCustomerOrderHdr(_id: String): Boolean {
        return Security.withAuth {
            customerOrderHdrService.deleteCustomerOrderHdr(_id)
        }
    }
}
