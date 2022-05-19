package com.fonrouge.remoteScreen

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.litote.kmongo.Id
import org.litote.kmongo.id.IdGenerator

object ObjectIdAsStringSerializer : KSerializer<Id<String>> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Id<Any>", PrimitiveKind.STRING)

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): Id<String> {
        return IdGenerator.defaultGenerator.create(decoder.decodeString()) as Id<String>
    }

    override fun serialize(encoder: Encoder, value: Id<String>) {
        encoder.encodeString(value.toString())
    }
}
