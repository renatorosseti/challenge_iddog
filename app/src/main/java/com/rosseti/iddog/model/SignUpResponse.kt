package com.rosseti.iddog.model

import com.google.gson.annotations.SerializedName


data class SignUpResponse(
    @SerializedName("token") val token: String = ""
)

data class LoginPostData(
    @SerializedName("email") var email: String
)