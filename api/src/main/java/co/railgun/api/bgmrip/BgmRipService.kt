package co.railgun.api.bgmrip

import co.railgun.api.bgmrip.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface BgmRipService {

    @POST("/api/user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    )
}
