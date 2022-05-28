package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.services.CasaDulceService
import io.kvision.state.ObservableValue

object Model {

    private val casaDulceService = CasaDulceService()

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
            casaDulceService.createNewCustomerOrderHdr()
        }
    }

    suspend fun addCustomerOrderItm(customerOrderItm: CustomerOrderItm) {
        casaDulceService.addCustomerOrderItm(customerOrderItm)
    }

    suspend fun getInventoryItm(_id: Int?): InventoryItm {
        return casaDulceService.getInventoryItm(_id)
    }
}
