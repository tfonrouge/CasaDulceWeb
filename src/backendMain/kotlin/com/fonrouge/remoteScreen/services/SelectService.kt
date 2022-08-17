package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.database.CustomerItmDb
import com.fonrouge.remoteScreen.database.InventoryItmDb
import com.fonrouge.remoteScreen.model.CustomerItm
import com.fonrouge.remoteScreen.model.InventoryItm
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
        val list = CustomerItmDb.collection.find(or(filter)).limit(100).toList()
        val result = list.map {
            val s = "<b>co</b>: ${it.company} <b>fn</b>: ${it.firstName} <b>ln</b>: ${it.lastName} - <i>${it._id}</i>"
            RemoteOption(
                value = it._id,
                text = s,
                content = s
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
        val list = InventoryItmDb.collection.find(or(filter)).limit(100).toList()
        val result = list.map {
            val s = "<b>upc</b>: ${it.upc} <b>name</b>: ${it.name} - <i>${it._id}</i>"
            RemoteOption(
                value = it._id.toString(),
                text = s,
                content = s
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
