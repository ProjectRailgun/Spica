package co.railgun.common.api

import co.railgun.common.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /**
     * DOH
     */
    @GET("dns-query")
    fun getIpByHost(
            @Query("name") host: String,
            @Query("type") type: String
                    ): Call<DnsResponse>

    /**
     * Users
     */

    @POST("api/user/login")
    fun login(@Body body: LoginRequest): Observable<MessageResponse>

    @POST("api/user/logout")
    fun logout(): Observable<MessageResponse>

    @GET("/api/user/info")
    fun getUserInfo(): Observable<DataResponse<UserInfo>>


    /**
     * Bangumi info
     */

    @GET("/api/home/my_bangumi")
    fun getMyBangumi(
        @Query("page") page: Int?,
        @Query("count") count: Int?,
        @Query("status") status: Int?
    ): Observable<ListResponse<Bangumi>>

    @GET("/api/home/announce")
    fun getAnnounceBangumi(): Observable<ListResponse<Announce>>

    @GET("/api/home/on_air")
    fun getOnAir(@Query("type") type: Int?): Observable<ListResponse<Bangumi>>

    @GET("/api/home/bangumi")
    fun getSearchBangumi(
        @Query("page") page: Int?,
        @Query("count") count: Int?,
        @Query("sort_field") sortField: String?,
        @Query("sort_order") sortOrder: String?,
        @Query("name") name: String?,
        @Query("type") type: Int?
    ): Observable<ListResponse<Bangumi>>

    @GET("/api/home/bangumi/{id}")
    fun getBangumiDetail(@Path("id") id: String): Observable<DataResponse<BangumiDetail>>

    @GET("/api/home/episode/{id}")
    fun getEpisodeDetail(@Path("id") id: String): Observable<EpisodeDetail>

    /**
     * Favorite and history
     */

    @POST("/api/watch/favorite/bangumi/{bangumi_id}")
    fun uploadFavoriteStatus(@Path("bangumi_id") bangumiId: String, @Body body: FavoriteChangeRequest): Observable<MessageResponse>

    @POST("/api/watch/history/synchronize")
    fun uploadWatchHistory(@Body body: HistoryChangeRequest): Observable<MessageResponse>
}