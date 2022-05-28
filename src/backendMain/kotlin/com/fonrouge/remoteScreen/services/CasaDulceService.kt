package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.CustomerOrderHdr
import com.fonrouge.remoteScreen.CustomerOrderItm
import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.database.*
import com.google.inject.Inject
import com.mongodb.client.model.UpdateOptions
import io.ktor.server.application.*
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteOption
import io.kvision.remote.RemoteSorter
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.litote.kmongo.regex
import java.time.LocalDateTime

actual class CasaDulceService : ICasaDulceService {

    @Inject
    lateinit var call: ApplicationCall

    override suspend fun getProfile() = call.withProfile { it }

    override suspend fun ping(hello: String): String {
        println("from frotend: $hello")
        val userProfile = call.withProfile { it }
        return "hello from server... ${userProfile.name}"
    }

    override suspend fun inventoryItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm> {
        return inventoryItmColl.buildRemoteData(page, size, filter, sorter, state)
    }

    override suspend fun customerItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerItm> {
        return customerItmColl.buildRemoteData(page, size, filter, sorter, state)
    }

    override suspend fun customerOrderHdrList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderHdr> {
        return customerOrderHdrColl.buildRemoteData(page, size, filter, sorter, state)
    }

    override suspend fun customerOrderItmByHdrList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderItm> {

        val aggInfo = AggInfo<CustomerOrderItm, InventoryItm>(
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

    override suspend fun selectCustomerItm(search: String?, initial: String?, state: String?): List<RemoteOption> {
        val list = mutableListOf<CustomerItm>()
        list.addAll(
            if (search == null && initial != null) {
                customerItmColl.find(CustomerItm::_id eq initial).toList()
            } else if (search != null) {
                customerItmColl.find(
                    or(
                        CustomerItm::_id.regex(search, "i"),
                        CustomerItm::company.regex(search, "i"),
                        CustomerItm::firstName.regex(search, "i"),
                        CustomerItm::lastName.regex(search, "i"),
                    )
                ).limit(100).toList()
            } else listOf()
        )
        val result = list.map {
            RemoteOption(
                value = it._id,
                content = "<b>co</b>: ${it.company} <b>fn</b>: ${it.firstName} <b>ln</b>: ${it.lastName} - <i>${it._id}</i>"
            )
        }.toMutableList()
        if (result.size == 100) {
            result.add(
                RemoteOption(
                    divider = true
                )
            )
            result.add(
                RemoteOption(
                    content = "<i>result is limited to 100 items ...</i>",
                    disabled = true
                )
            )
        }
        return result
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

    override suspend fun addCustomerOrderItm(customerOrderItm: CustomerOrderItm) {
        val doc = Document()
            .append(CustomerOrderItm::customerOrderHdr_id.name, customerOrderItm.customerOrderHdr_id)
            .append(CustomerOrderItm::inventoryItm.name, customerOrderItm.inventoryItm_id.toIntOrNull())
            .append(CustomerOrderItm::qty.name, customerOrderItm.qty)
            .append(CustomerOrderItm::size.name, customerOrderItm.size)
        customerOrderItmColl.updateOne(
            filter = Document(CustomerOrderItm::_id.name, ObjectId.get().toHexString()),
            update = Document("\$set", doc),
            options = UpdateOptions().upsert(true)
        )
    }

    override suspend fun selectInventoryItm(search: String?, initial: String?, state: String?): List<RemoteOption> {
        val list = mutableListOf<InventoryItm>()
        list.addAll(
            if (search == null && initial != null) {
                inventoryItmColl.find(InventoryItm::_id eq initial.toIntOrNull()).toList()
            } else if (search != null) {
                inventoryItmColl.find(
                    or(
                        InventoryItm::_id eq search.toIntOrNull(),
                        InventoryItm::name.regex(search, "i"),
                        InventoryItm::upc.regex(search, "i"),
                    )
                ).limit(100).toList()
            } else listOf()
        )
        val result = list.map {
            RemoteOption(
                value = it._id.toString(),
                content = "<b>upc</b>: ${it.upc} <b>name</b>: ${it.name} - <i>${it._id}</i>"
            )
        }.toMutableList()
        if (result.size == 100) {
            result.add(
                RemoteOption(
                    divider = true
                )
            )
            result.add(
                RemoteOption(
                    content = "<i>result is limited to 100 items ...</i>",
                    disabled = true
                )
            )
        }
        return result
    }

    override suspend fun getInventoryItm(_id: Int?): InventoryItm {
        /* will throw exception if not found */
        return inventoryItmColl.findOne(InventoryItm::_id eq _id)!!
    }
}
