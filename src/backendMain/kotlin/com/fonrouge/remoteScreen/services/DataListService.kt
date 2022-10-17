package com.fonrouge.remoteScreen.services

import com.fonrouge.fsLib.contextDataUrl
import com.fonrouge.fsLib.mongoDb.ModelLookup
import com.fonrouge.remoteScreen.database.*
import com.fonrouge.remoteScreen.model.*
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
        val firstStage = CustomerItmDb.listFirstStage(
            match = null,
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return CustomerItmDb.remoteData(firstStage = firstStage)
    }

    override suspend fun customerOrderHdr(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderHdr> {
        val firstStage = CustomerOrderHdrDb.listFirstStage(
            match = null,
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return CustomerOrderHdrDb.remoteData(
            firstStage = firstStage,
            ModelLookup(resultProperty = CustomerOrderHdr::customerItm)
        )
    }

    override suspend fun customerOrderItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderItm> {
        val firstStage = CustomerOrderItmDb.listFirstStage(
            match = match(CustomerOrderItm::customerOrderHdr_id eq state?.contextDataUrl?.contextIdValue()),
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return CustomerOrderItmDb.remoteData(
            firstStage = firstStage,
            ModelLookup(resultProperty = CustomerOrderItm::inventoryItm)
        )
    }

    override suspend fun inventoryItm(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm> {
        val firstStage = InventoryItmDb.listFirstStage(
            match = null,
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return InventoryItmDb.remoteData(firstStage = firstStage)
    }

    override suspend fun deliverList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<DeliveryOrderItm> {
        TODO("Not yet implemented")
    }

    override suspend fun deliveryHdr(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<DeliveryOrderHdr> {
        val firstStage = DeliveryOrderHdrDb.listFirstStage(
            match = null,
            page = page,
            size = size,
            filter = filter,
            sorter = sorter,
        )
        return DeliveryOrderHdrDb.remoteData(
            firstStage = firstStage,
            ModelLookup(resultProperty = DeliveryOrderHdr::customerItm)
        )
    }
}
