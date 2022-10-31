package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.annotations.MongoDoc
import com.fonrouge.fsLib.model.base.BaseModel
import com.fonrouge.fsLib.serializers.FSOffsetDateTimeSerializer
import io.kvision.types.OffsetDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@MongoDoc(collection = "shopImage")
@Serializable
class ShopifyImage(
    @JsonNames("id")
    override var _id: Long,
    @SerialName(value = "product_id")
    var productId: Long,
    var position: Int,
    @Serializable(with = FSOffsetDateTimeSerializer::class)
    @SerialName(value = "created_at")
    var createdAt: OffsetDateTime,
    @Serializable(with = FSOffsetDateTimeSerializer::class)
    @SerialName(value = "updated_at")
    var updated_at: OffsetDateTime,
    var alt: String?,
    var width: Int,
    var height: Int,
    var src: String,
    @SerialName(value = "variant_ids")
    var variantIds: ArrayList<Long> = ArrayList(),
    @SerialName(value = "admin_graphql_api_id")
    var adminGraphqlApiId: String,
) : BaseModel<Long>
