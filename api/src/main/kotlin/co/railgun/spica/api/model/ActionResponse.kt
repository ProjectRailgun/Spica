package co.railgun.spica.api.model

import kotlinx.serialization.Serializable

@Serializable(ActionResponseSerializer::class)
sealed class ActionResponse : Response {

    @Serializable
    object Ok : ActionResponse()

    @Serializable
    data class Message(
        val message: String,
    ) : ActionResponse()
}
