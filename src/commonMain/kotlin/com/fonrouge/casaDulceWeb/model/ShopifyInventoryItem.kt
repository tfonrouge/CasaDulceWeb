package com.fonrouge.casaDulceWeb.model

import com.fonrouge.fsLib.annotations.Collection
import com.fonrouge.fsLib.model.base.BaseDoc
import com.fonrouge.fsLib.serializers.FSOffsetDateTimeSerializer
import io.kvision.types.OffsetDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Collection(name = "shopifyInventoryItem")
@Serializable
class ShopifyInventoryItem(
    @JsonNames("id")
    override val _id: Long,
    var cost: Double,
    @SerialName("country_code_of_origin")
    var country_code_of_origin: String,
    @SerialName("country_harmonized_system_codes")
    var countryHarmonizedSystemCodes: String,
    @SerialName("created_at")
    @Serializable(with = FSOffsetDateTimeSerializer::class)
    var createdAt: OffsetDateTime,
    @SerialName("harmonized_system_code")
    var harmonizedSystemCode: String,
    var sku: String,
    var tracked: Boolean,
    @SerialName("updated_at")
    @Serializable(with = FSOffsetDateTimeSerializer::class)
    var updatedAt: OffsetDateTime,
    @SerialName("requires_shipping")
    var requiresShipping: Boolean
) : BaseDoc<Long>
