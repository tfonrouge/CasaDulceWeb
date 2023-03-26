package com.fonrouge.casaDulceWeb.model

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.model.base.BaseDoc
import io.kvision.types.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.EncodeDefault
import kotlin.js.JsExport

@kotlinx.serialization.Serializable
@JsExport
@Collection("deliveryOrderHdr")
class DeliveryOrderHdr(
    override val _id: String,
    val customerOrderItm_id: String,
    val customerItm_id: String,
    val qtyDelivered: Int,
    @Contextual
    val dateDelivered: LocalDateTime,
    val status: String
) : BaseDoc<String> {
    val customerOrderItm: CustomerOrderItm? = null

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val customerItm: CustomerItm? = null
    val statusDeliver: String
        get() {
            return deliveryStatusList.find { it.first == status }?.second ?: "?"
        }
}

val deliveryStatusList = listOf(
    "N" to "NewOrder",
    "PK" to "Picking",
    "PD" to "Picked",
    "T" to "InTransit",
    "#" to "Delivered"
)
