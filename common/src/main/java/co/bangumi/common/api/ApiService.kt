package co.bangumi.common.api

import co.bangumi.common.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by roya on 2017/5/22.
 */

interface ApiService {

    companion object {
        const val BANGUMI_TYPE_CN = 1001
        const val BANGUMI_TYPE_RAW = 1002
    }

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
    fun getMyBangumi(@Query("status") status: Int = 3): Observable<ListResponse<Bangumi>>

    @GET("/api/home/announce")
    fun getAnnounceBangumi(): Observable<ListResponse<Announce>>

    @GET("/api/home/on_air?type=1001")
    fun getOnAir(): Observable<ListResponse<Bangumi>>

    @GET("/api/home/bangumi")
    fun getSearchBangumi(
        @Query("page") page: Int?,
        @Query("count") count: Int?,
        @Query("sort_field") sortField: String?,
        @Query("sort_order") sortOrder: String?,
        @Query("name") name: String?
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