package kz.kazpost.loadingarea.api

import kz.kazpost.loadingarea.api._responses.TransportListResponse
import retrofit2.Response
import retrofit2.http.*

interface TransportApi {
    @Headers("Content-Type: application/json")
    @GET("get-all-for-send/")
    suspend fun getTransportList(@Query("fromDep") departmentId: String, @Query("fromDate") fromDate: String? = null, @Query("page") page: Int): Response<TransportListResponse>
}