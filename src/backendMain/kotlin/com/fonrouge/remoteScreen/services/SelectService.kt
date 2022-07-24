package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.CustomerItm
import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.database.customerItmDb
import com.fonrouge.remoteScreen.database.inventoryItmDb
import io.kvision.remote.RemoteOption
import org.bson.conversions.Bson
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.litote.kmongo.regex

actual class SelectService : ISelectService {

    override suspend fun customerItm(search: String?, initial: String?, state: String?): List<RemoteOption> {
        val filter = mutableListOf<Bson>()
        initial?.let { filter.add(CustomerItm::_id eq initial) }
        search?.let {
            filter.addAll(
                listOf(
                    CustomerItm::_id.regex(search, "i"),
                    CustomerItm::company.regex(search, "i"),
                    CustomerItm::firstName.regex(search, "i"),
                    CustomerItm::lastName.regex(search, "i"),
                )
            )
        }
        val list = customerItmDb.collection.find(or(filter)).limit(100).toList()
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

    override suspend fun inventoryItm(search: String?, initial: String?, state: String?): List<RemoteOption> {
        val filter = mutableListOf<Bson>()
        initial?.let { filter.add(InventoryItm::_id eq initial) }
        search?.let {
            filter.addAll(
                listOf(
                    InventoryItm::_id eq search,
                    InventoryItm::name.regex(search, "i"),
                    InventoryItm::upc.regex(search, "i"),
                )
            )
        }
        val list = inventoryItmDb.collection.find(or(filter)).limit(100).toList()
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
}
