package kz.kazpost.loadingarea.api

import kz.kazpost.loadingarea.api._requests.AuthRequest
import kz.kazpost.loadingarea.api._responses.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun makeAuthorization(@Body authRequest: AuthRequest): Response<AuthResponse>
}