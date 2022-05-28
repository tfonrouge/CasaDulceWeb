package com.fonrouge.remoteScreen

import io.kvision.types.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Transient

@kotlinx.serialization.Serializable
class CustomerOrderHdr(
    @Contextual
    val _id: String,
    val docId: Long,
    val customer: CustomerItm?,
    @Contextual
    val created: LocalDateTime,
    val status: String,
    val userProfile: String
) {
    @Transient
    val customer_id: String? = customer?._id
}
