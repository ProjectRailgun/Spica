package co.railgun.spica.api.internal.user

import co.railgun.spica.api.model.ActionResponse
import co.railgun.spica.api.model.DataResponse
import co.railgun.spica.api.model.user.LoginRequest
import co.railgun.spica.api.model.user.UserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface UserService {

    @POST("/api/user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): ActionResponse

    @GET("/api/user/info")
    suspend fun info(): DataResponse<UserInfo>
}
