package com.rosseti.iddog.model

import com.squareup.moshi.Json

data class SignUpResponse(
    @Json(name = "user") var user: User
): DomainMappable<SignUpResponse> {
    override fun asDomain() = SignUpResponse(user = user)
}

data class User(
    @Json(name = "token") var token: String
)