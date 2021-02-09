package kz.kazpost.loadingarea.api._responses

import com.google.gson.annotations.SerializedName

data class TransportResponse(

    @field:SerializedName("nextDepartment")
	val nextDepartment: DepartmentResponse? = null,

    @field:SerializedName("modifyBy")
	val modifyBy: String? = null,

    @field:SerializedName("fromDepartment")
	val fromDepartment: DepartmentResponse? = null,

    @field:SerializedName("modifyDate")
	val modifyDate: Long? = null,

    @field:SerializedName("toDepartment")
	val toDepartment: DepartmentResponse? = null,

    @field:SerializedName("flightStr")
	val flightStr: String? = null,

    @field:SerializedName("flightDTO")
	val flightDTO: FlightDTOResponse? = null,

    @field:SerializedName("currentIndex")
	val currentIndex: Int? = null,

    @field:SerializedName("createBy")
	val createBy: String? = null,

    @field:SerializedName("averageVolume")
	val averageVolume: Int? = null,

    @field:SerializedName("isAccepted")
	val isAccepted: Boolean? = null,

    @field:SerializedName("fromTime")
	val fromTime: String? = null,

    @field:SerializedName("transportListId")
	val transportListId: String? = null,

    @field:SerializedName("deliveryUser")
	val deliveryUser: String? = null,

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("containsSForCurrentGroup")
	val containsSForCurrentGroup: Boolean? = null,

    @field:SerializedName("createDate")
	val createDate: Long? = null,

    @field:SerializedName("status")
	val status: String? = null
)