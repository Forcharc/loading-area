package kz.kazpost.unloadingarea.data.responses

import com.google.gson.annotations.SerializedName

data class FlightDTOResponse(

	@field:SerializedName("secondPerformer")
	val secondPerformer: String? = null,

	@field:SerializedName("firstPerformer")
	val firstPerformer: String? = null,

	@field:SerializedName("toDate")
	val toDate: Long? = null,

	@field:SerializedName("fromDate")
	val fromDate: Long? = null,

	@field:SerializedName("createBy")
	val createBy: String? = null,

	@field:SerializedName("itineraryDTO")
	val itineraryDTO: ItineraryDTO? = null,

	@field:SerializedName("regNum")
	val regNum: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("fromTime")
	val fromTime: String? = null,

	@field:SerializedName("transportType")
	val transportType: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("department")
	val department: DepartmentResponse? = null,

	@field:SerializedName("createDate")
	val createDate: Long? = null,

	@field:SerializedName("status")
	val status: String? = null
)