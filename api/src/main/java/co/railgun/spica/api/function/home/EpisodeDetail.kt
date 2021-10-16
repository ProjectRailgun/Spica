@file:Suppress("unused")

package co.railgun.spica.api.function.home

import co.railgun.spica.api.SpicaApiClient
import co.railgun.spica.api.internal.dataResponse
import co.railgun.spica.api.internal.homeService
import co.railgun.spica.api.model.DataResponse
import co.railgun.spica.api.model.home.Episode


suspend fun SpicaApiClient.Home.episodeDetail(id: String): DataResponse<Episode> =
    dataResponse { homeService.episodeDetail(id) }
