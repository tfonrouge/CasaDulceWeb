package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.mongoDb.ModelLookup
import com.fonrouge.remoteScreen.database.customerItmDb
import com.fonrouge.remoteScreen.database.customerOrderHdrDb
import com.fonrouge.remoteScreen.database.customerOrderItmDb
import com.fonrouge.remoteScreen.database.inventoryItmDb
import com.fonrouge.remoteScreen.model.CustomerItm
import com.fonrouge.remoteScreen.model.CustomerOrderHdr
import com.fonrouge.remoteScreen.model.CustomerOrderItm
import com.fonrouge.remoteScreen.model.InventoryItm
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import org.litote.kmongo.eq
import org.litote.kmongo.match

actual class DataListService : IDataListService {
    override suspend fun customerItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerItm> {
        val firstStage = customerItmDb.buildFirstStage(
            match = null,
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return customerItmDb.remoteData(firstStage = firstStage)
    }

    override suspend fun customerOrderHdr(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderHdr> {
        val firstStage = customerOrderHdrDb.buildFirstStage(
            match = null,
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return customerOrderHdrDb.remoteData(
            firstStage = firstStage,
            modelLookupList = listOf(
                ModelLookup(
                    resultProperty = CustomerOrderHdr::customerItm
                )
            )
        )
    }

    override suspend fun customerOrderItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderItm> {
        val firstStage = customerOrderItmDb.buildFirstStage(
            match = state?.let { match(CustomerOrderItm::customerOrderHdr_id eq it) },
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return customerOrderItmDb.remoteData(
            firstStage = firstStage,
            modelLookupList = listOf(
                ModelLookup(
                    resultProperty = CustomerOrderItm::inventoryItm
                )
            )
        )
    }

    override suspend fun inventoryItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm> {
        val firstStage = inventoryItmDb.buildFirstStage(
            match = null,
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return inventoryItmDb.remoteData(firstStage = firstStage)
    }
}
