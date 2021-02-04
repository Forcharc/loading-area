package kz.kazpost.loadingarea.repositories.transport.models

import com.google.gson.annotations.SerializedName
import kz.kazpost.loadingarea.repositories.models.DepartmentResponse

data class ItineraryDTOEntryResponse(
    @field:SerializedName("dept")
	val dept: DepartmentResponse? = null,
)

