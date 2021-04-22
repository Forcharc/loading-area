package kz.kazpost.loadingarea.api._responses

import com.google.gson.annotations.SerializedName

data class GetWorkerInfoForTInvoiceResponse(
    @SerializedName("transportList")
    val transportInfo: TransportResponse,
    @SerializedName("result")
    val result: String,
    @SerializedName("currentTranportLists")
    val userTransports: List<TransportResponse>
)
