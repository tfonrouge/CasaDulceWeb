@file:OptIn(ExperimentalJsExport::class)

package com.fonrouge.casaDulceWeb.model

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.offsetDateTimeNow
import com.fonrouge.fsLib.serializers.FSOffsetDateTimeSerializer
import com.fonrouge.fsLib.serializers.OId
import io.kvision.types.OffsetDateTime
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
    override var _id: OId<CustomerOrderHdr> = OId(),
    override var numId: Int = 0,
    var customerItm_id: OId<CustomerItm> = OId(),
    @Suppress("NON_EXPORTABLE_TYPE")
    @Serializable(with = FSOffsetDateTimeSerializer::class)
    var created: OffsetDateTime = offsetDateTimeNow(),
    var status: String = "$",
    var userProfile: String = ""
) : DocumentWithNumId<OId<CustomerOrderHdr>> {

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
