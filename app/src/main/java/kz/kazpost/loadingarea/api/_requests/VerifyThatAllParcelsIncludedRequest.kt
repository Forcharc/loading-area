package kz.kazpost.loadingarea.api._requests

import com.google.gson.annotations.SerializedName

data class VerifyThatAllParcelsIncludedRequest(
    @field:SerializedName(value = "items")
    val currentlyIncludedShpis: List<String>,

    @field:SerializedName(value = "id")
    val tInvoiceId: Long,

    @field:SerializedName(value = "department")
    val userDepartmentId: String,

    @field:SerializedName(value = "index")
    val index: Int
)
