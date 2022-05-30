package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.database.buildRemoteData
import com.fonrouge.remoteScreen.database.customerItmColl
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteOption
import io.kvision.remote.RemoteSorter
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.litote.kmongo.regex

actual class CustomerItmService : ICustomerItmService {

    override suspend fun customerItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerItm> {
        return customerItmColl.buildRemoteData(page, size, filter, sorter, state)
    }

    override suspend fun selectCustomerItm(search: String?, initial: String?, state: String?): List<RemoteOption> {
        println("$search, $initial, $state")
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
}
