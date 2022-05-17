package com.fonrouge.remoteScreen

import io.kvision.types.LocalDateTime
import kotlinx.serialization.Contextual

@kotlinx.serialization.Serializable
class CustomerOrder(
    val id: String,
    val productId: String,
    val qty: Int,
    val unit: String,
    @Contextual
    val created: LocalDateTime
) {
}
