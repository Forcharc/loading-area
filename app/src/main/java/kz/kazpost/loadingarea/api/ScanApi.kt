package kz.kazpost.loadingarea.api

import kz.kazpost.loadingarea.api._requests.DecoupleMissingParcelsRequest
import kz.kazpost.loadingarea.api._requests.VerifyThatAllParcelsIncludedRequest
import kz.kazpost.loadingarea.api._responses.GetTInvoiceInfoResponse
import kz.kazpost.loadingarea.api._responses.ResultResponse
import kz.kazpost.loadingarea.api._responses.VerifyThatAllParcelsIncludedResponse
import retrofit2.Response
import retrofit2.http.*

interface ScanApi {
    @Headers("Content-Type: application/json")
    @GET("get-info-TList/")
    suspend fun getTInvoiceInfo(
        @Query("tList") tInvoiceNumber: String,
        @Query("index") index: Int
    ): Response<GetTInvoiceInfoResponse>

    @Headers("Content-Type: application/json")
    @POST("verific-plan-fact/")
    suspend fun verifyThatAllParcelsIncluded(@Body request: VerifyThatAllParcelsIncludedRequest): Response<VerifyThatAllParcelsIncludedResponse>

    @Headers("Content-Type: application/json")
    @POST("exclude-mails-and-labellists-from-tls")
    suspend fun decoupleMissingParcelsFromTInvoice(@Body request: DecoupleMissingParcelsRequest): Response<ResultResponse>

}