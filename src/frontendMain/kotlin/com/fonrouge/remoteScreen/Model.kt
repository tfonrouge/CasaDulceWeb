package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CasaDulceService
import com.fonrouge.remoteScreen.services.CustomerOrderHdrService
import com.fonrouge.remoteScreen.services.CustomerOrderItmService
import com.fonrouge.remoteScreen.services.InventoryItmService
import io.kvision.state.ObservableValue

object Model {

    private val casaDulceService = CasaDulceService()
    private val customerOrderHdrService = CustomerOrderHdrService()
    private val customerOrderItmService = CustomerOrderItmService()
    private val inventoryItmService = InventoryItmService()

    val obsProfile = ObservableValue(UserProfile())

    suspend fun ping(hello: String): String {
        return Security.withAuth {
            casaDulceService.ping(hello)
        }
    }

    suspend fun readProfile() {
        obsProfile.value = casaDulceService.getProfile()
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

    suspend fun addCustomerOrderItm(customerOrderItm: CustomerOrderItm) {
        customerOrderItmService.addCustomerOrderItm(customerOrderItm)
    }

    suspend fun getInventoryItm(_id: Int?): InventoryItm {
        return inventoryItmService.getInventoryItm(_id)
    }
}
