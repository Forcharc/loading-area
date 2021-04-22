package kz.kazpost.loadingarea.api

import kz.kazpost.loadingarea.api._requests.SetWorkerForTransportRequest
import kz.kazpost.loadingarea.api._responses.GetWorkerInfoForTInvoiceResponse
import kz.kazpost.loadingarea.api._responses.ResultResponse
import kz.kazpost.loadingarea.api._responses.TransportListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TransportApi {
    @Headers("Content-Type: application/json")
    @GET("get-all-for-send/")
    suspend fun getTransportList(
        @Query("fromDep") departmentId: String,
        @Query("fromDate") fromDate: String? = null,
        @Query("page") page: Int
    ): Response<TransportListResponse>



    @Headers("Content-Type: application/json")
    @GET("get-modifyby-of-tl")
    suspend fun getWorkerInfoFotTInvoice(@Query("transportListId", ) tInvoiceNumber: String, @Query("userName") login: String): Response<GetWorkerInfoForTInvoiceResponse>

}