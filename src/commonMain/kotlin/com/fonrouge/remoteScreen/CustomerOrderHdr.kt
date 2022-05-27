package com.fonrouge.remoteScreen

import io.kvision.types.LocalDateTime
import kotlinx.serialization.Contextual

@kotlinx.serialization.Serializable
class CustomerOrderHdr(
    @Contextual
    val _id: String,
    val docId: String,
    val customer: CustomerItm,
    @Contextual
    val created: LocalDateTime,
    val status: String
)
