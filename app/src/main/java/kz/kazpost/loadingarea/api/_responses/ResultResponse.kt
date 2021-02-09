package kz.kazpost.loadingarea.api._responses

import com.google.gson.annotations.SerializedName

data class ResultResponse(
	@field:SerializedName("result")
	val result: String? = null
) {
	fun isSuccessful(): Boolean {
		return result == "success"
	}
}
