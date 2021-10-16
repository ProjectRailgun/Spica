@file:Suppress("unused")

package co.railgun.spica.api.function.home

import co.railgun.spica.api.SpicaApiClient
import co.railgun.spica.api.internal.dataResponse
import co.railgun.spica.api.internal.homeService
import co.railgun.spica.api.model.DataResponse
import co.railgun.spica.api.model.home.Bangumi

suspend fun SpicaApiClient.Home.bangumiDetail(id: String): DataResponse<Bangumi> =
    dataResponse { homeService.bangumiDetail(id) }
