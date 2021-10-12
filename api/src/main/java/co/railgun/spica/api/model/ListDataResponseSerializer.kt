package co.railgun.spica.api.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject

@OptIn(
    ExperimentalSerializationApi::class,
    InternalSerializationApi::class,
)
class ListDataResponseSerializer<T>(
    private val dataSerializer: KSerializer<T>,
) : KSerializer<ListDataResponse<T>> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("Response", PolymorphicKind.SEALED) {
            element("Ok", dataSerializer.descriptor)
        }

    override fun deserialize(decoder: Decoder): ListDataResponse<T> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val json = decoder.json
        require(element is JsonObject && "data" in element.keys && element["data"] is JsonArray) { "Unsupported." }
        return ListDataResponse.Ok(
            json.decodeFromString(ListSerializer(dataSerializer), element["data"].toString())
        )
    }

    override fun serialize(encoder: Encoder, value: ListDataResponse<T>): Unit =
        error("Unsupported.")
}
