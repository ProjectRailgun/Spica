package co.railgun.spica.data.bangumi

import co.railgun.spica.api.SpicaApiClient
import co.railgun.spica.api.function.home.announcedBangumi
import co.railgun.spica.api.function.home.myBangumi
import co.railgun.spica.api.function.home.onAir
import co.railgun.spica.api.model.ListDataResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BangumiRepository {

    private val _announcedBangumi: MutableStateFlow<List<Bangumi>> =
        MutableStateFlow(emptyList())
    val announcedBangumi: StateFlow<List<Bangumi>> = _announcedBangumi.asStateFlow()

    private val _myBangumi: MutableStateFlow<List<Bangumi>> = MutableStateFlow(emptyList())
    val myBangumi: StateFlow<List<Bangumi>> = _myBangumi.asStateFlow()

    private val _onAir: MutableStateFlow<List<Bangumi>> = MutableStateFlow(emptyList())
    val onAir: StateFlow<List<Bangumi>> = _onAir.asStateFlow()

    private val _updateState: MutableStateFlow<UpdateState> = MutableStateFlow(UpdateState.NoError)
    val updateState: StateFlow<UpdateState> = _updateState.asStateFlow()

    suspend fun updateBangumiState() {
        updateAnnouncedBangumi()
        updateMyBangumi()
        updateOnAir()
    }

    suspend fun updateAnnouncedBangumi() =
        update(
            response = { SpicaApiClient.Home.announcedBangumi() },
            emit = { _announcedBangumi.emit(data.toAnnouncedBangumi()) },
        )

    suspend fun updateMyBangumi() =
        update(
            response = { SpicaApiClient.Home.myBangumi() },
            emit = { _myBangumi.emit(data.toBangumi()) },
        )

    suspend fun updateOnAir() =
        update(
            response = { SpicaApiClient.Home.onAir() },
            emit = { _onAir.emit(data.toBangumi()) },
        )

    private suspend inline fun <reified T> update(
        response: () -> ListDataResponse<T>,
        emit: ListDataResponse.Ok<T>.() -> Unit,
    ) = when (val listDataResponse = response()) {
        is ListDataResponse.Ok -> listDataResponse.emit()
        is ListDataResponse.Unauthorized -> _updateState.emit(UpdateState.Unauthorized)
        is ListDataResponse.Error -> _updateState.emit(UpdateState.Error(listDataResponse.exception))
    }
}

val bangumiRepository: BangumiRepository by lazy { BangumiRepository() }
