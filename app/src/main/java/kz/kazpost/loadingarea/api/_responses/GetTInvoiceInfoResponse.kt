package kz.kazpost.loadingarea.api._responses

import com.google.gson.annotations.SerializedName

data class GetTInvoiceInfoResponse(

	@field:SerializedName("mailItems")
	val mailItems: List<MailResponse?>? = null,

	@field:SerializedName("labelDataList")
	val labelDataList: List<LabelDataItemResponse?>? = null,

	@field:SerializedName("index")
	val index: Int? = null,

	@field:SerializedName("nextDep")
	val nextDep: String? = null,

	@field:SerializedName("itemCount")
	val itemCount: Int? = null,

	@field:SerializedName("labels")
	val labels: LabelsResponse? = null,

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("labelItems")
	val labelItems: List<LabelItemResponse?>? = null,

	@field:SerializedName("road")
	val road: List<RoadItem?>? = null,

	@field:SerializedName("fromDep")
	val fromDep: String? = null,

	@field:SerializedName("transportListId")
	val transportListId: String? = null,

	@field:SerializedName("items")
	val items: List<String?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)


data class MailResponse(

	@field:SerializedName("mailType")
	val mailType: String? = null,

	@field:SerializedName("fromDepartment")
	val fromDepartment: String? = null,

	@field:SerializedName("toDepartment")
	val toDepartment: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("mailId")
	val mailId: String? = null,

	@field:SerializedName("hasAct")
	val hasAct: Boolean? = null,

	@field:SerializedName("packetId")
	val packetId: String? = null
)

data class RoadItem(

	@field:SerializedName("index")
	val index: Int? = null,

	@field:SerializedName("dept")
	val dept: DepartmentResponse? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null
)

data class LabelsResponse(

	@field:SerializedName("taraBag")
	val taraBag: Int? = null,

	@field:SerializedName("gazeta")
	val gazeta: Int? = null,

	@field:SerializedName("strBag")
	val strBag: Int? = null,

	@field:SerializedName("ppiBag")
	val ppiBag: Int? = null,

	@field:SerializedName("kgpo")
	val kgpo: Int? = null,

	@field:SerializedName("otherBag")
	val otherBag: Int? = null,

	@field:SerializedName("rpoCarefull")
	val rpoCarefull: Int? = null,

	@field:SerializedName("prvBag")
	val prvBag: Int? = null,

	@field:SerializedName("rpo")
	val rpo: Int? = null,

	@field:SerializedName("emsBag")
	val emsBag: Int? = null,

	@field:SerializedName("packetList")
	val packetList: Int? = null,

	@field:SerializedName("rpoEconom")
	val rpoEconom: Int? = null,

	@field:SerializedName("korBag")
	val korBag: Int? = null
)

data class LabelItemResponse(

	@field:SerializedName("fromDepartment")
	val fromDepartment: String? = null,

	@field:SerializedName("toDepartment")
	val toDepartment: String? = null,

	@field:SerializedName("labelType")
	val labelType: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("labelListId")
	val labelListId: String? = null,

	@field:SerializedName("hasAct")
	val hasAct: Boolean? = null
)

data class LabelDataItemResponse(

	@field:SerializedName("labelType")
	val labelType: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("count")
	val count: Int? = null
)
