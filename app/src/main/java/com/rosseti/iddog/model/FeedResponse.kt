package com.rosseti.iddog.model

import com.squareup.moshi.Json

data class FeedResponse (
    @Json(name = "category") var category: String,
    @Json(name = "list") var list: List<String>
): DomainMappable<FeedResponse> {
    override fun asDomain() = FeedResponse(category, list)
}
