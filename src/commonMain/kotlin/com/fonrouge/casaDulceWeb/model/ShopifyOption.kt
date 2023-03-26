package com.fonrouge.casaDulceWeb.model

import com.fonrouge.fsLib.annotations.Collection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Collection(name = "shopOption")
@Serializable
class ShopifyOption(
    var id: Long,
    @SerialName(value = "product_id")
    var productId: Long,
    var name: String,
    var position: Int,
    var values: Array<String>,
)
