package kz.kazpost.unloadingarea.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("groups")
    val groups: List<GroupsItem?>? = null,

    @SerializedName("sessioID")
    val sessionId: String? = null
)

data class GroupsItem(
    @SerializedName("role")
    val role: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("group")
    val group: String? = null
)
