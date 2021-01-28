package kz.kazpost.unloadingarea.data.responses

import com.google.gson.annotations.SerializedName

data class ItineraryDTO(

    @field:SerializedName("itineraryType")
	val itineraryType: String? = null,

    @field:SerializedName("fromDepartment")
	val fromDepartment: DepartmentResponse? = null,

    @field:SerializedName("createBy")
	val createBy: String? = null,

    @field:SerializedName("haveFlight")
	val haveFlight: Boolean? = null,

    @field:SerializedName("toDepartment")
	val toDepartment: DepartmentResponse? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("transportType")
	val transportType: String? = null,

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("department")
	val department: DepartmentResponse? = null,

    @field:SerializedName("createDate")
	val createDate: Long? = null
)