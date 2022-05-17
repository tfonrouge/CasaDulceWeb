package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.Product
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter

actual class ProductCatalogService : IProductCatalogService {

    override suspend fun products(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<Product> {
        return RemoteData(listOf())
    }
}
