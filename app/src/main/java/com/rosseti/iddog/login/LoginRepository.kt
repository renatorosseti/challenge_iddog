package com.rosseti.iddog.login

import com.rosseti.iddog.api.IddogApi
import com.rosseti.iddog.model.AppRxSchedulers
import com.rosseti.iddog.model.SignUpResponse
import io.reactivex.Single
import javax.inject.Inject

class LoginRepository
@Inject constructor(
    private val api: IddogApi,
    private val rxSchedulers: AppRxSchedulers) {

    fun loadUserEmail(email: String): Single<SignUpResponse> {
        return api.signUp(email)
            .subscribeOn(rxSchedulers.network)
            .observeOn(rxSchedulers.main)
    }
}