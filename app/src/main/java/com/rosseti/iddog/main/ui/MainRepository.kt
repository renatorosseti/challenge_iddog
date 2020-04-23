package com.rosseti.iddog.main.ui

import com.rosseti.iddog.api.IddogApi
import com.rosseti.iddog.base.BaseRepository
import com.rosseti.iddog.model.AppRxSchedulers
import com.rosseti.iddog.model.FeedResponse
import io.reactivex.Single
import javax.inject.Inject

class MainRepository
@Inject constructor(
    private val api: IddogApi,
    private val rxSchedulers: AppRxSchedulers) : BaseRepository() {

    fun loadFeed(category: String, apiToken: String): Single<FeedResponse> {
        return api.feed(category = category, apiToken = apiToken)
            .compose(mapNetworkErrors())
            .mapToDomain()
            .subscribeOn(rxSchedulers.network)
            .observeOn(rxSchedulers.main)
    }
}