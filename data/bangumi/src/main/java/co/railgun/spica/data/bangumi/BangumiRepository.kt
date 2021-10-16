package co.railgun.spica.data.bangumi

import co.railgun.spica.api.SpicaApiClient
import co.railgun.spica.api.function.home.announcedBangumi
import co.railgun.spica.api.function.home.bangumiDetail
import co.railgun.spica.api.function.home.episodeDetail
import co.railgun.spica.api.function.home.myBangumi
import co.railgun.spica.api.function.home.onAir
import co.railgun.spica.api.model.DataResponse
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

    suspend fun bangumiDetail(id: String): Detail<Bangumi> =
        updateDetail(
            response = { SpicaApiClient.Home.bangumiDetail(id = id) },
            onSuccess = { it.toData() },
        )

    suspend fun episodeDetail(id: String): Episode =
        SpicaApiClient.Home.episodeDetail(id = id).toData()

    private suspend inline fun <reified T> update(
        response: () -> ListDataResponse<T>,
        emit: ListDataResponse.Ok<T>.() -> Unit,
    ) = when (val listDataResponse = response()) {
        is ListDataResponse.Ok -> listDataResponse.emit()
        is ListDataResponse.Unauthorized -> _updateState.emit(UpdateState.Unauthorized)
        is ListDataResponse.Error -> _updateState.emit(UpdateState.Error(listDataResponse.exception))
    }

    @Suppress("UNCHECKED_CAST")
    private inline fun <reified T, R> updateDetail(
        response: () -> DataResponse<T>,
        onSuccess: (T) -> R,
    ): Detail<R> = when (val dataResponse = response()) {
        is DataResponse.Ok -> Detail.Result(data = onSuccess(dataResponse.data))
        is DataResponse.Unauthorized -> Detail.Unauthorized as Detail<R>
        is DataResponse.Error -> Detail.Error(exception = dataResponse.exception)
    }
}

val bangumiRepository: BangumiRepository by lazy { BangumiRepository() }
