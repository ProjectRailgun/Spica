package co.railgun.api.bgmrip.internal.user

import co.railgun.api.bgmrip.model.DataResponse
import co.railgun.api.bgmrip.model.user.LoginRequest
import co.railgun.api.bgmrip.model.user.UserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface UserService {

    @POST("/api/user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    )

    @GET("/api/user/info")
    suspend fun info(): DataResponse<UserInfo>
}
