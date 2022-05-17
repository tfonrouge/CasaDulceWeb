package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.Product
import com.fonrouge.remoteScreen.tables.Products
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

actual class ProductCatalogService : IProductCatalogService {

    override suspend fun products(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<Product> {
        val list = transaction {
            Products.selectAll().toList()
        }
        println(list)
        return RemoteData(listOf())
    }

    override suspend fun agregaProducto(product: Product) {
        transaction {
            val id = Products.insertAndGetId {
                it[description] = product.description
                it[unit] = product.unit
            }
            println("inserted product with id = '$id'")
        }
    }
}
