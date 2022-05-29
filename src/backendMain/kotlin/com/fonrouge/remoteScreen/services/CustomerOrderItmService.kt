package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerOrderItm
import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.database.AggInfo
import com.fonrouge.remoteScreen.database.buildRemoteData
import com.fonrouge.remoteScreen.database.customerOrderItmColl
import com.fonrouge.remoteScreen.database.inventoryItmColl
import com.mongodb.client.model.UpdateOptions
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import org.bson.Document
import org.bson.types.ObjectId

actual class CustomerOrderItmService : ICustomerOrderItmService {

    override suspend fun customerOrderItmByHdrList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderItm> {

        val aggInfo = AggInfo(
            from = inventoryItmColl,
            localField = CustomerOrderItm::inventoryItm,
            foreignField = InventoryItm::_id
        )

        return customerOrderItmColl.buildRemoteData(
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
            state = state,
            aggInfo = aggInfo
        )
    }

    override suspend fun addCustomerOrderItm(customerOrderItm: CustomerOrderItm): Boolean {
        val doc = Document()
            .append(CustomerOrderItm::customerOrderHdr_id.name, customerOrderItm.customerOrderHdr_id)
            .append(CustomerOrderItm::inventoryItm.name, customerOrderItm.inventoryItm_id.toIntOrNull())
            .append(CustomerOrderItm::qty.name, customerOrderItm.qty)
            .append(CustomerOrderItm::size.name, customerOrderItm.size)
        val result = customerOrderItmColl.updateOne(
            filter = Document(CustomerOrderItm::_id.name, ObjectId.get().toHexString()),
            update = Document("\$set", doc),
            options = UpdateOptions().upsert(true)
        )
        return result.upsertedId != null
    }
}
