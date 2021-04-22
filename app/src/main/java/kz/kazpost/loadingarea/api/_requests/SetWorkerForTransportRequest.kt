package kz.kazpost.loadingarea.api._requests

import com.google.gson.annotations.SerializedName

data class SetWorkerForTransportRequest(

    @SerializedName("transportListId")
    val transportListId: String,

    @SerializedName("user")
    val user: String,

    @SerializedName("department")
    val department: String
)
