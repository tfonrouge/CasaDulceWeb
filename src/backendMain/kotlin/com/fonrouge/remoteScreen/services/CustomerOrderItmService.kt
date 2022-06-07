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
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.eq
import org.litote.kmongo.match
import org.litote.kmongo.setValue
import org.litote.kmongo.setValueOnInsert

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

    override suspend fun deleteCustomerOrderItm(_id: String): Boolean {
        val deleteResult = customerOrderItmColl.deleteOne(CustomerOrderItm::_id eq _id)
        return deleteResult.deletedCount == 1L
    }

    override suspend fun updateFieldQty(_id: String, value: Int) : Boolean {
        val result = customerOrderItmColl.updateOne(
            filter = CustomerOrderItm::_id eq _id,
            update = setValue(property = CustomerOrderItm::qty, value = value)
        )
        return result.modifiedCount == 1L
    }

    override suspend fun updateFieldSize(_id: String, value: String) {
    val result = customerOrderItmColl.updateOne(
        filter = CustomerOrderItm::_id eq _id,
        update = setValue(property = CustomerOrderItm::size, value = value)
        )
    }
}
