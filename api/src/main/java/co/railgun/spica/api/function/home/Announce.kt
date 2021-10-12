@file:Suppress("unused")

package co.railgun.spica.api.function.home

import co.railgun.spica.api.SpicaApiClient
import co.railgun.spica.api.internal.homeService
import co.railgun.spica.api.internal.listDataResponse
import co.railgun.spica.api.model.ListDataResponse
import co.railgun.spica.api.model.home.Announce

suspend fun SpicaApiClient.Home.announcedBangumi(): ListDataResponse<Announce> =
    listDataResponse { homeService.announce() }
