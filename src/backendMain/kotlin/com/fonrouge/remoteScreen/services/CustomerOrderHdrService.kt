package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.CustomerOrderHdr
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

    val aggLookup: AggLookup<CustomerOrderHdr, CustomerItm> = AggLookup(
        from = customerItmColl,
        localField = CustomerOrderHdr::customerItm_id,
        foreignField = CustomerItm::_id,
        newAs = CustomerOrderHdr::customerItm
    )

    private suspend fun getProfile() = call.withProfile { it }

    override suspend fun customerOrderHdrItem(_id: String): CustomerOrderHdr {
        return customerOrderHdrColl.aggItem(_id, aggLookup)!!
    }

    override suspend fun customerOrderHdrList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderHdr> {

        return customerOrderHdrColl.buildRemoteData(page, size, filter, sorter, state, aggLookup)
    }

    override suspend fun createNewCustomerOrderHdr(): CustomerOrderHdr {

        val userProfileId = getProfile().id ?: ""

        val customerOrderHdr: CustomerOrderHdr? =
            customerOrderHdrColl.findOne(
                CustomerOrderHdr::userProfile eq userProfileId,
                CustomerOrderHdr::status eq "$"
            )

        if (customerOrderHdr != null) return customerOrderHdr

        val docId = customerOrderHdrColl.find().descendingSort(CustomerOrderHdr::docId).limit(1).first()?.let {
            it.docId + 1
        } ?: 1

        return CustomerOrderHdr(
            _id = ObjectId.get().toHexString(),
            docId = docId,
            created = LocalDateTime.now(),
            customerItm_id = null,
            status = "$",
            userProfile = userProfileId
        ).also {
            customerOrderHdrColl.insertOne(it)
        }
    }

    override suspend fun updateCustomerOrderHdr(customerOrderHdr: CustomerOrderHdr): Boolean {
        customerOrderHdr.customerItm = null
        val r = customerOrderHdrColl.updateOne(
            filter = Document(CustomerOrderHdr::_id.name, customerOrderHdr._id),
            target = customerOrderHdr
        )
        println("RESULT = $r")
        return r.upsertedId != null
    }
}
