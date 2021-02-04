package kz.kazpost.loadingarea.repositories.models

import com.google.gson.annotations.SerializedName

data class DepartmentResponse(

    @field:SerializedName("parent")
	val parent: DepartmentResponse? = null,

    @field:SerializedName("address")
	val address: String? = null,

    @field:SerializedName("longNameRu")
	val longNameRu: String? = null,

    @field:SerializedName("techindex")
	val techindex: String? = null,

    @field:SerializedName("isNotAutomation")
	val isNotAutomation: Boolean? = null,

    @field:SerializedName("telephone")
	val telephone: String? = null,

    @field:SerializedName("type")
	val type: String? = null,

    @field:SerializedName("headTechindex")
	val headTechindex: String? = null,

    @field:SerializedName("enabled")
	val enabled: Boolean? = null,

    @field:SerializedName("isOfficial")
	val isOfficial: Boolean? = null,

    @field:SerializedName("headName")
	val headName: String? = null,

    @field:SerializedName("name")
	val name: String? = null,

    @field:SerializedName("postindex")
	val postindex: String? = null,

    @field:SerializedName("isDressingRoom")
	val isDressingRoom: Boolean? = null
)