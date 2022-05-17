package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.Product
import com.fonrouge.remoteScreen.tables.Products
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

actual class ProductCatalogService : IProductCatalogService {

    override suspend fun products(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<Product> {
        Database.connect()
        val list = Products.selectAll().toList()
        return RemoteData(listOf())
    }

    override suspend fun agregaProducto(product: Product) {
        Products.insert {
            it[description] = product.description
            it[unit] = product.unit
        }
    }
}
