package kz.kazpost.unloadingarea.repositories.transport

import kz.kazpost.unloadingarea.repositories.transport.models.TransportListResponse
import retrofit2.Response
import retrofit2.http.*

interface TransportApi {
    @Headers("Content-Type: application/json")
    @GET("get-all-for-send/")
    suspend fun getTransportList(@Query("fromDep") departmentId: String, @Query("fromDate") fromDate: String? = null): Response<TransportListResponse>
}