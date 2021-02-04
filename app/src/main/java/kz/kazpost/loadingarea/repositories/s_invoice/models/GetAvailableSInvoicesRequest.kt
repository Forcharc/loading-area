package kz.kazpost.loadingarea.repositories.s_invoice.models

import com.google.gson.annotations.SerializedName

data class GetAvailableSInvoicesRequest(

	@field:SerializedName("fromDate")
	val fromDate: String,

	@field:SerializedName("entryDeps")
	val entryDeps: List<String>,

	@field:SerializedName("toDate")
	val toDate: String,

	@field:SerializedName("userName")
	val userName: String,

	@field:SerializedName("department")
	val department: String
)
