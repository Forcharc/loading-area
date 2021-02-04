package kz.kazpost.loadingarea.repositories.s_invoice.models

import com.google.gson.annotations.SerializedName
import kz.kazpost.loadingarea.repositories.models.DepartmentResponse


data class SInvoiceResponse(

    @field:SerializedName("isMjd")
    val isMjd: Boolean? = null,

    @field:SerializedName("modifyBy")
    val modifyBy: String? = null,

    @field:SerializedName("fromDepartment")
    val fromDepartment: DepartmentResponse? = null,

    @field:SerializedName("modifyDate")
    val modifyDate: Long? = null,

    @field:SerializedName("toDepartment")
    val toDepartment: DepartmentResponse? = null,

    @field:SerializedName("quantityPalletsWithCorrespondence")
    val quantityPalletsWithCorrespondence: Int? = null,

    @field:SerializedName("totalNumberOfLabels")
    val totalNumberOfLabels: Int? = null,

    @field:SerializedName("destinationStatus")
    val destinationStatus: String? = null,

    @field:SerializedName("destinationListId")
    val destinationListId: String? = null,

    @field:SerializedName("createBy")
    val createBy: String? = null,

    @field:SerializedName("averageVolume")
    val averageVolume: Int? = null,

    @field:SerializedName("totalWeight")
    val totalWeight: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("totalNumberOfPLs")
    val totalNumberOfPLs: Int? = null,

    @field:SerializedName("quantityContainersWithCorrespondence")
    val quantityContainersWithCorrespondence: Int? = null,

    @field:SerializedName("createDate")
    val createDate: Long? = null,

    @field:SerializedName("totalNumberMails")
    val totalNumberMails: Int? = null,

    @field:SerializedName("numberContainerWithCorrespondence")
    val numberContainerWithCorrespondence: String? = null
)
