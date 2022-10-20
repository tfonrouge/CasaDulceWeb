package com.fonrouge.remoteScreen.model

import com.fonrouge.fsLib.serializers.FSOffsetDateTimeSerializer
import io.kvision.types.OffsetDateTime
import kotlinx.serialization.Serializable

@Serializable
class SLocation(
    var id: Long,
    var name: String,
    var address1: String,
    var address2: String,
    var city: String,
    var zip: String,
    var province: String,
    var country: String,
    var phone: String,
    @Serializable(with = FSOffsetDateTimeSerializer::class)
    var created_at: OffsetDateTime,
    @Serializable(with = FSOffsetDateTimeSerializer::class)
    var updated_at: OffsetDateTime,
    var country_code: String,
    var country_name: String,
    var province_code: String,
    var legacy: Boolean,
    var active: Boolean,
    var admin_graphql_api_id: String,
    var localized_country_name: String,
    var localized_province_name: String,
)
