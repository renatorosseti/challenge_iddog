package com.rosseti.iddog.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rosseti.iddog.data.Cache
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class LoginViewModel (
    private val repository: LoginRepository): ViewModel() {

    val response = MutableLiveData<LoginViewState>()

    private val disposable = CompositeDisposable()

    private val TAG = LoginViewModel::class.simpleName

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun checkEmailLogin(email: String) {
        response.value = LoginViewState.ShowLoadingState
        val loadUserEmail: Disposable = repository.loadUserEmail(email)
            .subscribe (
                {
                    if (it.user != null && it.user.token.isNotEmpty()) {
                        Cache.apiToken = it.user.token
                        Log.i(TAG, "token ${Cache.apiToken}")
                        response.value = LoginViewState.ShowMainScreen
                    } else {
                        response.value = LoginViewState.ShowRequestError(it.error.message)
                    }
                },
                {
                    response.value = LoginViewState.ShowRequestError("Error: Email not valid.")
                    Log.e(TAG,"Error: ${it.message}")
                }
            )
        disposable.add(loadUserEmail)
    }
}