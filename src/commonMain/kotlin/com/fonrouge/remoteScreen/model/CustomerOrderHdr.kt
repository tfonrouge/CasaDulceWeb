@file:OptIn(ExperimentalJsExport::class)

package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.annotations.MongoDoc
import com.fonrouge.fsLib.newObjectId
import com.fonrouge.fsLib.offsetDateTimeNow
import com.fonrouge.fsLib.serializers.FSOffsetDateTimeSerializer
import io.kvision.types.OffsetDateTime
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsExport
@MongoDoc("customerOrderHdrs")
data class CustomerOrderHdr(
    override var _id: String = newObjectId(),
    override var numId: Int = 0,
    var customerItm_id: String = "",
    @Suppress("NON_EXPORTABLE_TYPE")
    @Serializable(with = FSOffsetDateTimeSerializer::class)
    var created: OffsetDateTime = offsetDateTimeNow(),
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
