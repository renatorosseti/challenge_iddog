package com.rosseti.iddog.model

import com.squareup.moshi.Json

data class SignUpResponse(
    @Json(name = "user") var user: User,
    @Json(name = "error") var error: Error
)

data class User(
    @Json(name = "token") var token: String
)

data class Error(
    @Json(name = "message") var message: String
)