@file:Suppress("unused")

package co.railgun.spica.api.function.home

import co.railgun.spica.api.SpicaApiClient
import co.railgun.spica.api.internal.homeService
import co.railgun.spica.api.internal.listDataResponse
import co.railgun.spica.api.model.ListDataResponse
import co.railgun.spica.api.model.home.Bangumi

suspend fun SpicaApiClient.Home.onAir(
    type: BangumiType = BangumiType.ALL,
): ListDataResponse<Bangumi> =
    listDataResponse {
        val queryMap = mapOf(
            "type" to type.value.toString(),
        )
        homeService.onAir(queryMap)
    }
