package kz.kazpost.unloadingarea.repositories.add_s_invoice

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SInvoiceApi {
    @Headers("Content-Type: application/json")
    @GET("get-dls-for-tl/")
    suspend fun getAvailableSInvoices(@Query("userName") userName: String?, @Query("department") department: String?): Response<Any>

}