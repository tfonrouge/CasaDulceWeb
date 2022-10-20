package com.fonrouge.remoteScreen.model

import io.kvision.types.OffsetDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class ShopifyImage(
    var id: Long,
    var product_id: Long,
    var position: Int,
    @Contextual
    var created_at: OffsetDateTime,
    @Contextual
    var updated_at: OffsetDateTime,
    var alt: String,
    var width: Int,
    var height: Int,
    var src: String,
    var variant_ids: ArrayList<Long> = ArrayList(),
    var admin_graphql_api_id: String,
)
