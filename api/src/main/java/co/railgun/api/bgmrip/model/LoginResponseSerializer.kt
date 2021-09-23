package co.railgun.api.bgmrip.model

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
class LoginResponseSerializer : KSerializer<LoginResponse> {

    private val successSerializer by lazy { LoginResponse.Success.serializer() }
    private val failureSerializer by lazy { LoginResponse.Failure.serializer() }

    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("LoginResponse", PolymorphicKind.SEALED) {
            element("Success", successSerializer.descriptor)
            element("Failure", failureSerializer.descriptor)
        }

    override fun deserialize(decoder: Decoder): LoginResponse {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        require(element is JsonObject)
        return when {
            "message" in element ->
                LoginResponse.Failure(element["message"]!!.jsonPrimitive.content)
            else -> LoginResponse.Success
        }
    }

    override fun serialize(encoder: Encoder, value: LoginResponse): Unit =
        throw UnsupportedOperationException()
}
