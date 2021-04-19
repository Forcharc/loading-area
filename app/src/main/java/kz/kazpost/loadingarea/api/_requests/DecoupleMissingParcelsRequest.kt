package kz.kazpost.loadingarea.api._requests

import com.google.gson.annotations.SerializedName

data class DecoupleMissingParcelsRequest(
    @SerializedName("items")
    val mailsAndLabels: MailsAndLabelsWrapper,
    @SerializedName("transportListId")
    val transportListId: String,
    @SerializedName("user")
    val user: String
)

data class MailsAndLabelsWrapper(
    @SerializedName("mailsAndLabels")
    val mailsAndLabels: List<String>
)
