package kz.kazpost.unloadingarea.data.responses

import com.google.gson.annotations.SerializedName

data class TransportListResponse(
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("transports")
    val transportList: List<TransportResponse>
)
