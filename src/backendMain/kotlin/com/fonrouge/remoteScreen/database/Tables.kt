package com.fonrouge.remoteScreen.database

import com.fonrouge.fsLib.mongoDb.*
import com.fonrouge.remoteScreen.model.*
import com.mongodb.client.model.IndexOptions
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.Id

object Tables : ITables {
    val CustomerItmDb = object : CTableDb<CustomerItm, String>(
        klass = CustomerItm::class
    ) {}
    val CustomerOrderHdrDb = object : CTableDb<CustomerOrderHdr, String>(
        klass = CustomerOrderHdr::class
    ) {
        override val lookupFun = {
            listOf(
                lookupField(
                    cTableDb = CustomerItmDb::class,
                    localField = CustomerOrderHdr::customerItm_id,
                    foreignField = CustomerItm::_id,
                    resultField = CustomerOrderHdr::customerItm
                )
            )
        }

        init {
            runBlocking {
                coroutineColl.ensureUniqueIndex(
                    CustomerOrderHdr::numId
                )
            }
        }
    }
    val CustomerOrderItmDb = object : CTableDb<CustomerOrderItm, String>(
        klass = CustomerOrderItm::class
    ) {
        override val lookupFun = {
            listOf(
                lookupField(
                    cTableDb = InventoryItmDb::class,
                    localField = CustomerOrderItm::inventoryItm_id,
                    foreignField = InventoryItm::_id,
                    resultField = CustomerOrderItm::inventoryItm
                )
            )
        }

        init {
            runBlocking {
                coroutineColl.ensureUniqueIndex(
                    properties = arrayOf(CustomerOrderItm::customerOrderHdr_id, CustomerOrderItm::inventoryItm_id),
                )
            }
        }
    }
    val DeliveryItmDb = object : CTableDb<DeliveryOrderItm, String>(
        klass = DeliveryOrderItm::class
    ) {}
    val DeliveryOrderHdrDb = object : CTableDb<DeliveryOrderHdr, String>(
        klass = DeliveryOrderHdr::class
    ) {
        override val lookupFun: () -> List<LookupPipelineBuilder<DeliveryOrderHdr, *, *>> = {
            listOf(
                lookupField(
                    cTableDb = CustomerItmDb::class,
                    localField = DeliveryOrderHdr::customerItm_id,
                    foreignField = CustomerItm::_id,
                    resultField = DeliveryOrderHdr::customerItm
                )
            )
        }

        init {
            runBlocking {
                coroutineColl.ensureUniqueIndex(
                    DeliveryOrderHdr::customerOrderItm_id
                )
            }
        }
    }
    val InventoryItmDb = object : CTableDb<InventoryItm, String>(
        klass = InventoryItm::class
    ) {
        init {
            runBlocking {
                coroutineColl.ensureIndex(InventoryItm::name, indexOptions = IndexOptions().collation(collation()))
                coroutineColl.ensureIndex(InventoryItm::upc, indexOptions = IndexOptions().collation(collation()))
            }
        }
    }
    val ShopifyProductDb = object : CTableDb<ShopifyProduct, Long>(
        klass = ShopifyProduct::class
    ) {
        override val lookupFun: (() -> List<LookupPipelineBuilder<ShopifyProduct, *, *>>) = {
            listOf(
                lookupFieldArray(
                    cTableDb = ShopifyVariantDb::class,
                    localField = ShopifyProduct::_id,
                    foreignField = ShopifyVariant::product_id,
                    resultFieldArray = ShopifyProduct::variants
                )
            )
        }

        init {
            constLookupList = listOf(
                ShopifyProduct::variants
            )
        }
    }
    val ShopifyVariantDb = object : CTableDb<ShopifyVariant, Long>(
        klass = ShopifyVariant::class
    ) {}
    val ShopifyImageDb = object : CTableDb<ShopifyImage, Long>(
        klass = ShopifyImage::class
    ) {}
    val UserItmDb = object : CTableDb<UserItm, Id<UserItm>>(
        klass = UserItm::class
    ) {}
}

suspend fun getNextNumId(cTableDb: CTableDb<out DocumentWithNumId<String>, String>): Int {
    return cTableDb.coroutineColl.find()
        .descendingSort(DocumentWithNumId<*>::numId)
        .limit(1)
        .first()?.let { it.numId + 1 } ?: 1
}
