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
import kotlinx.serialization.json.jsonPrimitive

@OptIn(
    ExperimentalSerializationApi::class,
    InternalSerializationApi::class,
)
class ActionResponseSerializer : KSerializer<ActionResponse> {

    private val okSerializer by lazy { ActionResponse.Ok.serializer() }
    private val messageSerializer by lazy { ActionResponse.Message.serializer() }

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("Response", PolymorphicKind.SEALED) {
            element("Ok", okSerializer.descriptor)
            element("Message", messageSerializer.descriptor)
        }

    override fun deserialize(decoder: Decoder): ActionResponse {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        require(element is JsonObject)
        return when {
            "msg" in element && element["msg"]?.jsonPrimitive?.content == "OK" -> ActionResponse.Ok
            "message" in element -> ActionResponse.Message(element["message"]!!.jsonPrimitive.content)
            else -> error("Unsupported.")
        }
    }

    override fun serialize(encoder: Encoder, value: ActionResponse): Unit =
        throw UnsupportedOperationException()
}
