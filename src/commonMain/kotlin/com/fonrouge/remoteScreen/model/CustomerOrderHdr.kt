@file:OptIn(ExperimentalJsExport::class)

package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.serializers.FSLocalDateTimeSerializer
import io.kvision.types.LocalDateTime
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsExport
@Collection("customerOrderHdrs")
data class CustomerOrderHdr(
    override var _id: String = "",
    override var numId: Int = 0,
    var customerItm_id: String,
    @Suppress("NON_EXPORTABLE_TYPE")
    @Serializable(with = FSLocalDateTimeSerializer::class)
    var created: LocalDateTime,
    var status: String = "$",
    var userProfile: String = ""
) : DocumentWithNumId<String> {

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    var customerItm: CustomerItm? = null

    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val statusLabel: String?
        get() {
            return customerOrderHdrStatusList.find { it.first == status }?.second
        }
}

val customerOrderHdrStatusList = listOf(
    "1" to "Pending Order",
    "0" to "Finished Order",
    "C" to "Cancelled Order",
    "$" to "New Order",
    "2" to "Order Ready"
)
