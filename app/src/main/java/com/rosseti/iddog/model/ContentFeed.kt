package com.rosseti.iddog.model

import com.squareup.moshi.Json

data class ContentFeed (
    @Json(name = "category") var category: String,
    @Json(name = "list") var list: List<String>
)