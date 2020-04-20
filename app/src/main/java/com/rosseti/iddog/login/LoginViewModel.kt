package com.rosseti.iddog.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rosseti.iddog.data.Cache
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel (val repository: LoginRepository): ViewModel() {

    val disposable = CompositeDisposable()

    val response = MutableLiveData<LoginViewState>()

    val TAG = LoginViewModel::class.simpleName

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun checkEmailLogin(email: String) {
        disposable.add(repository.loadUserEmail(email)
            .subscribe (
                {
                    Log.i(TAG,"Aqui estou")
                    Cache.cacheApiToken(it.token)
                    response.value = LoginViewState(it.token)},
                { e -> Log.e(TAG,"error:  ${e.message} + ${e.cause}")}
            )
        )
    }

}