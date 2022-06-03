package com.fonrouge.remoteScreen

import com.fonrouge.remoteScreen.CustomerOrderHdr.Companion.customerOrderHdrStatusList
import io.kvision.types.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.EncodeDefault

@kotlinx.serialization.Serializable
data class CustomerOrderHdr(
    override val _id: String,
    val docId: Int,
    val customerItm_id: String?,
    @Contextual
    val created: LocalDateTime,
    var status: String,
    val userProfile: String
) : IBase<String> {
    var customerItm: CustomerItm? = null

    @EncodeDefault
    val statusLabel: String get() =  run {
        val element: Pair<String, String>? = customerOrderHdrStatusList.find { pair ->
            pair.first == status
        }
        if (element == null) {
            return "no hubo"
        } else {
            return element.second
        }
    }
    companion object {
        val customerOrderHdrStatusList = listOf(
            "1" to "Pending Order",
            "0" to "Finished Order",
            "C" to "Cancelled Order",
            "$" to "New Order"
        )
    }
}
