package kz.kazpost.loadingarea.api._responses

import com.google.gson.annotations.SerializedName

data class VerifyThatAllParcelsIncludedResponse(
    @field:SerializedName("result")
    val result: String? = null,

    @field:SerializedName("itemsList")
    val missingShpis: List<String?>? = null,
) {
    fun isSuccessful(): Boolean {
        return result == "success"
    }
}
