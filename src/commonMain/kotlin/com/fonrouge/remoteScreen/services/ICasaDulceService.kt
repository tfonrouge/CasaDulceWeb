package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.*
import io.kvision.annotations.KVService
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteOption
import io.kvision.remote.RemoteSorter

@KVService
interface ICasaDulceService {

    suspend fun getProfile(): UserProfile

    suspend fun ping(hello: String): String

    suspend fun inventoryItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<InventoryItm>

    suspend fun customerItmList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerItm>

    suspend fun customerOrderHdrList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderHdr>

    suspend fun customerOrderItmByHdrList(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<CustomerOrderItm>

    suspend fun selectCustomerItm(
        search: String?,
        initial: String?,
        state: String?
    ): List<RemoteOption>

    suspend fun createNewCustomerOrderHdr(): CustomerOrderHdr

    suspend fun addCustomerOrderItm(customerOrderItm: CustomerOrderItm)

    suspend fun selectInventoryItm(
        search: String?,
        initial: String?,
        state: String?
    ): List<RemoteOption>

    suspend fun getInventoryItm(_id: Int?): InventoryItm
}
