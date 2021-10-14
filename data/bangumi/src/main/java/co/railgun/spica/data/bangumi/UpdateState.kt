package co.railgun.spica.data.bangumi

sealed interface UpdateState {

    object NoError : UpdateState

    object Unauthorized : UpdateState

    data class Error(
        val exception: Throwable,
    ) : UpdateState
}
