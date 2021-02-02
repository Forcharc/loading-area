package kz.kazpost.unloadingarea.repositories.transport.models

import com.google.gson.annotations.SerializedName

data class ItineraryDTOEntryResponse(
	@field:SerializedName("dept")
	val dept: DepartmentResponse? = null,
)

