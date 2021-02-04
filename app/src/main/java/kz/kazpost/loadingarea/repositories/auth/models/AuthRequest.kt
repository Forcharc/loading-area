package kz.kazpost.loadingarea.repositories.auth.models

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String
)
