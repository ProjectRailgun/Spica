package co.railgun.spica.api.internal.home

import co.railgun.spica.api.model.DataResponse
import co.railgun.spica.api.model.ListDataResponse
import co.railgun.spica.api.model.home.Announce
import co.railgun.spica.api.model.home.Bangumi
import co.railgun.spica.api.model.home.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

internal interface HomeService {

    @GET("/api/home/my_bangumi")
    fun myBangumi(
        @QueryMap queryMap: Map<String, String>,
    ): ListDataResponse<Bangumi>

    @GET("/api/home/announce")
    fun announce(): ListDataResponse<Announce>

    @GET("/api/home/on_air")
    fun onAir(
        @QueryMap queryMap: Map<String, String>,
    ): ListDataResponse<Bangumi>

    @GET("/api/home/bangumi")
    fun searchBangumi(
        @QueryMap queryMap: Map<String, String>,
    ): ListDataResponse<Bangumi>

    @GET("/api/home/bangumi/{id}")
    fun bangumiDetail(
        @Path("id") id: String,
    ): DataResponse<Bangumi>

    @GET("/api/home/episode/{id}")
    fun episodeDetail(
        @Path("id") id: String,
    ): DataResponse<Episode>
}
