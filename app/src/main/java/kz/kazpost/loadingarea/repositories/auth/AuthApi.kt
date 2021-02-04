package kz.kazpost.loadingarea.repositories.auth

import kz.kazpost.loadingarea.repositories.auth.models.AuthRequest
import kz.kazpost.loadingarea.repositories.auth.models.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun makeAuthorization(@Body authRequest: AuthRequest): Response<AuthResponse>
}