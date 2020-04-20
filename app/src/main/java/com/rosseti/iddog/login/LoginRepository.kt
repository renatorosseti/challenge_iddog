package com.rosseti.iddog.login

import com.rosseti.iddog.api.TmdbApi
import com.rosseti.iddog.model.AppRxSchedulers
import com.rosseti.iddog.model.LoginPostData
import com.rosseti.iddog.model.SignUpResponse
import io.reactivex.Observable
import javax.inject.Inject

class LoginRepository
@Inject constructor(val api: TmdbApi, val rxSchedulers: AppRxSchedulers) {

    fun loadUserEmail(email: String): Observable<SignUpResponse> {
        return api.signUp(LoginPostData(email))
            .subscribeOn(rxSchedulers.network)
            .observeOn(rxSchedulers.main)
    }
}