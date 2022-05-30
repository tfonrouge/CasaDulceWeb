package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerOrderItm
import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.database.AggLookup
import com.fonrouge.remoteScreen.database.buildRemoteData
import com.fonrouge.remoteScreen.database.customerOrderItmColl
import com.fonrouge.remoteScreen.database.inventoryItmColl
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.match

actual class CustomerOrderItmService : ICustomerOrderItmService {

    override suspend fun customerOrderItmByHdrList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderItm> {

        val aggLookup = AggLookup(
            from = inventoryItmColl,
            localField = CustomerOrderItm::inventoryItm_id,
            foreignField = InventoryItm::_id,
            newAs = CustomerOrderItm::inventoryItm
        )


        return customerOrderItmColl.buildRemoteData(
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
            match = state?.let { match(CustomerOrderItm::customerOrderHdr_id eq state) },
            aggLookup = aggLookup
        )
    }

    override suspend fun addCustomerOrderItm(customerOrderItm: CustomerOrderItm): Boolean {
        customerOrderItm._id = ObjectId.get().toHexString()
        customerOrderItm.inventoryItm = null
        val r = customerOrderItmColl.insertOne(customerOrderItm)
        println("RESULT = $r")
        return r.insertedId != null
    }
}
