package com.rosseti.iddog.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(val repository: LoginRepository): ViewModel() {

    val disposable = CompositeDisposable()

    val response = MutableLiveData<LoginViewState>()

    val TAG = LoginViewModel::class.simpleName

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun checkEmailLogin() {
        disposable.add(repository.loadUserEmail()
            .subscribe (
                { response.value = LoginViewState(it)},
                { e -> Log.e(TAG,"error:  $e")}
            )
        )
    }

}