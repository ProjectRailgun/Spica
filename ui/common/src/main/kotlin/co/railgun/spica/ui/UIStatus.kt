package co.railgun.spica.ui

sealed class UIStatus

data class UIError(
    val message: String,
) : UIStatus()

fun UIError(throwable: Throwable): UIError =
    UIError(throwable.message ?: "Error occurred: $throwable")
