package com.fonrouge.remoteScreen

import io.kvision.types.LocalDateTime
import kotlinx.serialization.Contextual

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
}
