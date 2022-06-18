package com.fonrouge.remoteScreen

import io.kvision.types.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.EncodeDefault
import kotlin.js.JsExport

@kotlinx.serialization.Serializable
@JsExport
data class CustomerOrderHdr(
    override var _id: String,
    var docId: Int,
    var customerItm_id: String?,
    @Contextual
    var created: LocalDateTime,
    var status: String,
    var userProfile: String
) : IBase<String> {
    var customerItm: CustomerItm? = null

    var statusLabel: String = "?"
        get() {
            return customerOrderHdrStatusList.find { it.first == status }?.second ?: "?"
        }
        set(value) {
            field = value
        }
}

val customerOrderHdrStatusList = listOf(
    "1" to "Pending Order",
    "0" to "Finished Order",
    "C" to "Cancelled Order",
    "$" to "New Order"
)
