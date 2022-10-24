package com.fonrouge.remoteScreen.model

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@JsExport
class ShopifyVariant(
    var id: Long = 0,
    var product_id: Long = 0,
    var title: String? = null,
    var body_html: String? = null,
    var price: Double,
    var sku: String,
    var position: Int = 0,
    var inventory_policy: String? = null,
    var compare_at_price: Double? = null,
    var fulfillment_service: String? = null,
    var inventory_management: String? = null,
    var option1: String? = null,
    var option2: String? = null,
    var option3: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var taxable: Boolean? = null,
    var barcode: String? = null,
    var grams: Int? = null,
    var image_id: Long? = null,
    var weight: Double,
    var weight_unit: String? = null,
    var inventory_item_id: Long = 0,
    var inventory_quantity: Int,
    var old_inventory_quantity: Int? = null,
    var requires_shipping: Boolean? = null,
    var admin_graphql_api_id: String? = null,
)
