package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.InventoryItm
import com.fonrouge.remoteScreen.database.buildRemoteData
import com.fonrouge.remoteScreen.database.inventoryItmColl
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteOption
import io.kvision.remote.RemoteSorter
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.litote.kmongo.regex

actual class InventoryItmService : IInventoryItmService {

    override suspend fun inventoryItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm> {
        return inventoryItmColl.buildRemoteData(page, size, filter, sorter)
    }

    override suspend fun selectInventoryItm(search: String?, initial: String?, state: String?): List<RemoteOption> {
        val list = mutableListOf<InventoryItm>()
        list.addAll(
            if (search == null && initial != null) {
                inventoryItmColl.find(InventoryItm::_id eq initial).toList()
            } else if (search != null) {
                inventoryItmColl.find(
                    or(
                        InventoryItm::_id eq search,
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

    override suspend fun getInventoryItm(_id: String): InventoryItm {
        /* will throw exception if not found */
        return inventoryItmColl.findOne(InventoryItm::_id eq _id)!!
    }
}
