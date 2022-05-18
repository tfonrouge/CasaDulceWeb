package com.fonrouge.remoteScreen.services

import com.fonrouge.remoteScreen.Product
import com.fonrouge.remoteScreen.tables.TableProduct
import io.kvision.remote.RemoteData
import io.kvision.remote.RemoteFilter
import io.kvision.remote.RemoteSorter
import io.kvision.remote.ServiceException
import org.jetbrains.exposed.exceptions.ExposedSQLException
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
            TableProduct.all().map {
                Product(
                    id = it.id.value,
                    code = it.code,
                    description = it.description,
                    unit = it.unit
                )
            }
        }
        return RemoteData(list)
    }

    override suspend fun agregaProducto(product: Product) {
        transaction {
            TableProduct.new {
                code = product.code.uppercase()
                description = product.description
                unit = product.unit
            }
            try {
                commit()
            } catch (e: ExposedSQLException) {
                println("error on product create = ${e.message}")
                throw ServiceException("error on product create = ${e.message}")
            }
        }
    }
}
