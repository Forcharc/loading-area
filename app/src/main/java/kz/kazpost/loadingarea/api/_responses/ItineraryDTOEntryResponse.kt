package kz.kazpost.loadingarea.api._responses

import com.google.gson.annotations.SerializedName

data class ItineraryDTOEntryResponse(
    @field:SerializedName("dept")
	val dept: DepartmentResponse? = null,
)

