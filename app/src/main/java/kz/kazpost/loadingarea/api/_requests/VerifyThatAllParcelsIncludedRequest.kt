package kz.kazpost.loadingarea.api._requests

import com.google.gson.annotations.SerializedName

data class VerifyThatAllParcelsIncludedRequest(
    @field:SerializedName(value = "items")
    val currentlyIncludedShpis: List<String>,

    @field:SerializedName(value = "id")
    val tInvoiceId: Int,

    @field:SerializedName(value = "department")
    val userDepartmentId: String
)
