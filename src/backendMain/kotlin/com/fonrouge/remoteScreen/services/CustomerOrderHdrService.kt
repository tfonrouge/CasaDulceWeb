package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.CustomerOrderHdr
import com.fonrouge.remoteScreen.CustomerOrderItm
import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.database.*
import com.google.inject.Inject
import io.ktor.server.application.*
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import java.time.LocalDateTime

actual class CustomerOrderHdrService : ICustomerOrderHdrService {

    @Inject
    lateinit var call: ApplicationCall

    private suspend fun getProfile() = call.withProfile { it }

    override suspend fun customerOrderHdrList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderHdr> {

        val aggInfo = AggInfo(
            from = customerItmColl,
            localField = CustomerOrderHdr::customer,
            foreignField = CustomerItm::_id
        )

        return customerOrderHdrColl.buildRemoteData(page, size, filter, sorter, state, aggInfo)
    }

    override suspend fun createNewCustomerOrderHdr(): CustomerOrderHdr {

        val userProfileId = getProfile().id ?: ""

        var customerOrderHdr: CustomerOrderHdr? =
            customerOrderHdrColl.findOne(
                CustomerOrderHdr::userProfile eq userProfileId,
                CustomerOrderHdr::status eq "$"
            )

        if (customerOrderHdr == null) {
            val docId = customerOrderHdrColl.find().descendingSort(CustomerOrderHdr::docId).limit(1).first()?.let {
                it.docId + 1
            } ?: 1
            customerOrderHdr = CustomerOrderHdr(
                _id = ObjectId.get().toHexString(),
                docId = docId,
                customer = null,
                created = LocalDateTime.now(),
                status = "$",
                userProfile = userProfileId
            )
            customerOrderHdrColl.insertOne(customerOrderHdr)
        }
        return customerOrderHdr
    }

    override suspend fun updateCustomerOrderHdr(_id: String, json: String): Boolean {
        val d = Document()
            .append("\$set", Document.parse(json))
        val r = customerOrderHdrColl.updateOne(CustomerOrderHdr::_id eq _id, update = d)
        return r.upsertedId != null
    }
}
