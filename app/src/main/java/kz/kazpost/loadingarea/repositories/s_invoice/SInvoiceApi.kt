package kz.kazpost.loadingarea.repositories.s_invoice

import kz.kazpost.loadingarea.repositories.models.ResultResponse
import kz.kazpost.loadingarea.repositories.s_invoice.models.AddSInvoicesToTInvoiceRequest
import kz.kazpost.loadingarea.repositories.s_invoice.models.GetAvailableSInvoicesRequest
import kz.kazpost.loadingarea.repositories.s_invoice.models.SInvoiceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SInvoiceApi {
    @Headers("Content-Type: application/json")
    @POST("get-dls-for-tl/")
    suspend fun getAvailableSInvoices(@Body request: GetAvailableSInvoicesRequest): Response<List<SInvoiceResponse>>


    @Headers("Content-Type: application/json")
    @POST("attach-dl-to-tl/")
    suspend fun addSInvoiceToTInvoice(@Body request: AddSInvoicesToTInvoiceRequest): Response<ResultResponse>
}