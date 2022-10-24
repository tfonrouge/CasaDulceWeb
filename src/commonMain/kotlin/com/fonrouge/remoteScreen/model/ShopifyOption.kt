package com.fonrouge.remoteScreen.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ShopifyOption(
    var id: Long,
    @SerialName(value = "product_id")
    var productId: Long,
    var name: String,
    var position: Int,
    var values: Array<String>,
)
