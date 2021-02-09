package kz.kazpost.loadingarea.api._requests

import com.google.gson.annotations.SerializedName

data class AddSInvoicesToTInvoiceRequest(
    @field:SerializedName("dlId")
    val sInvoiceIdsSeparatedByComma: String,

    @field:SerializedName("tlId")
    val tInvoiceId: Int,

    @field:SerializedName("averageVolume")
    val averageVolume: Long,

    @field:SerializedName("userName")
    val userName: String
)
