package com.rosseti.iddog.main.ui

import com.rosseti.iddog.api.IddogApi
import com.rosseti.iddog.model.AppRxSchedulers
import com.rosseti.iddog.model.ContentFeed
import io.reactivex.Single
import javax.inject.Inject

class MainRepository
@Inject constructor(
    private val api: IddogApi,
    private val rxSchedulers: AppRxSchedulers) {

    fun loadFeed(apiToken: String): Single<ContentFeed> {
        return api.feed(apiToken)
            .subscribeOn(rxSchedulers.network)
            .observeOn(rxSchedulers.main)
    }
}