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
import io.kvision.remote.RemoteOption
import io.kvision.remote.RemoteSorter
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.litote.kmongo.regex

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
        return customerOrderItmColl.buildRemoteData(page, size, filter, sorter, state)
    }

    override suspend fun selectCustomerItm(search: String?, initial: String?, state: String?): List<RemoteOption> {
        val list = mutableListOf<CustomerItm>()
        list.addAll(
            if (initial != null) {
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
}
