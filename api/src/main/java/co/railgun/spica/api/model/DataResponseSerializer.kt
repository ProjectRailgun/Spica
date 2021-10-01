package co.railgun.spica.api.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject

@OptIn(
    ExperimentalSerializationApi::class,
    InternalSerializationApi::class,
)
class DataResponseSerializer<T>(
    private val dataSerializer: KSerializer<T>,
) : KSerializer<DataResponse<T>> {

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("Response", PolymorphicKind.SEALED) {
            element("Ok", dataSerializer.descriptor)
        }

    override fun deserialize(decoder: Decoder): DataResponse<T> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val json = decoder.json
        require(element is JsonObject)
        return when {
            "data" in element.keys ->
                DataResponse.Ok(json.decodeFromJsonElement(dataSerializer, element["data"]!!))
            else -> error("Unsupported.")
        }
    }

    override fun serialize(encoder: Encoder, value: DataResponse<T>): Unit =
        error("Unsupported.")
}
