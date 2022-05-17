package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.Product
import io.kvision.annotations.KVService
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter

@KVService
interface IProductCatalogService {
    suspend fun products(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<Product>

    suspend fun agregaProducto(product: Product)
}
