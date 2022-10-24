package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.model.base.BaseModel
import com.fonrouge.fsLib.serializers.FSOffsetDateTimeSerializer
import io.kvision.types.OffsetDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
class ShopifyProduct(
    @SerialName("id")
    override var _id: Long,
    @SerialName("body_html")
    var bodyHtml: String,
    @SerialName("created_at")
    @Serializable(with = FSOffsetDateTimeSerializer::class)
    var createdAt: OffsetDateTime,
    var handle: String,
    var images: Array<ShopifyImage> = emptyArray(),
    var image: ShopifyImage?,
    var options: Array<ShopifyOption> = emptyArray(),
    @SerialName(value = "product_type")
    var productType: String,
    @SerialName(value = "published_at")
    @Serializable(with = FSOffsetDateTimeSerializer::class)
    var publishedAt: OffsetDateTime?,
    @SerialName(value = "published_scope")
    var publishedScope: String,
    var status: String,
    var tags: String,
    @SerialName(value = "template_suffix")
    var templateSuffix: String?,
    var title: String,
    @SerialName(value = "updated_at")
    @Serializable(with = FSOffsetDateTimeSerializer::class)
    var updatedAt: OffsetDateTime,
    var variants: Array<ShopifyVariant> = emptyArray(),
    var vendor: String,
    @SerialName(value = "admin_graphql_api_id")
    var adminGraphqlApiId: String,
) : BaseModel<Long>
