package com.rosseti.iddog.login

import io.reactivex.Single

class LoginRepository {

    fun loadUserEmail(): Single<String> {
        return Single.just("Verify email on `id`dog server.")
    }
}