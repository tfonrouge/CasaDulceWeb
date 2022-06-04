package com.fonrouge.remoteScreen

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
    val statusLabel: String = customerOrderHdrStatusList.find { it.first == status }?.second ?: "?"

    companion object {
        val customerOrderHdrStatusList = listOf(
            "1" to "Orden Pendiente",
            "0" to "Finished Order",
            "C" to "Cancelled Order",
            "$" to "New Order"
        )
    }
}
