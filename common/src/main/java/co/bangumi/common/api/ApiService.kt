package co.bangumi.common.api

import co.bangumi.common.model.Announce
import co.bangumi.common.model.Bangumi
import co.bangumi.common.model.UserInfo
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by roya on 2017/5/22.
 */

interface ApiService {

    companion object {
        val BANGUMI_TYPE_CN = 1001
        val BANGUMI_TYPE_RAW = 1002
    }

    /**
     * Users
     */

    @POST("api/user/login")
    fun login(@Body body: co.bangumi.common.api.LoginRequest): Observable<co.bangumi.common.api.MessageResponse>

    @POST("api/user/logout")
    fun logout(): Observable<co.bangumi.common.api.MessageResponse>

    @GET("/api/user/info")
    fun getUserInfo(): Observable<co.bangumi.common.api.DataResponse<UserInfo>>


    /**
     * Bangumi info
     */

    @GET("/api/home/my_bangumi")
    fun getMyBangumi(@Query("status") status: Int = 3): Observable<co.bangumi.common.api.ListResponse<Bangumi>>

    @GET("/api/home/announce")
    fun getAnnounceBangumi(): Observable<co.bangumi.common.api.ListResponse<Announce>>

    @GET("/api/home/on_air")
    fun getAllBangumi(): Observable<co.bangumi.common.api.ListResponse<Bangumi>>

    @GET("/api/home/bangumi")
    fun getSearchBangumi(
        @Query("page") page: Int?,
        @Query("count") count: Int?,
        @Query("sort_field") sortField: String?,
        @Query("sort_order") sortOrder: String?,
        @Query("name") name: String?
    ): Observable<co.bangumi.common.api.ListResponse<Bangumi>>

    @GET("/api/home/bangumi/{id}")
    fun getBangumiDetail(@Path("id") id: String): Observable<co.bangumi.common.api.DataResponse<co.bangumi.common.model.BangumiDetail>>

    @GET("/api/home/episode/{id}")
    fun getEpisodeDetail(@Path("id") id: String): Observable<co.bangumi.common.model.EpisodeDetail>

    /**
     * Favorite and history
     */

    @POST("/api/watch/favorite/bangumi/{bangumi_id}")
    fun uploadFavoriteStatus(@Path("bangumi_id") bangumiId: String, @Body body: co.bangumi.common.api.FavoriteChangeRequest): Observable<co.bangumi.common.api.MessageResponse>

    @POST("/api/watch/history/synchronize")
    fun uploadWatchHistory(@Body body: co.bangumi.common.api.HistoryChangeRequest): Observable<co.bangumi.common.api.MessageResponse>
}