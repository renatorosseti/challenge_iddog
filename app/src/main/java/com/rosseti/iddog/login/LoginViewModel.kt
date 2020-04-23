package com.rosseti.iddog.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.rosseti.iddog.R
import com.rosseti.iddog.data.Cache
import com.rosseti.iddog.model.HttpCallFailureException
import com.rosseti.iddog.model.NoNetworkException
import com.rosseti.iddog.model.ServerUnreachableException

class LoginViewModel (
    private val repository: LoginRepository): ViewModel() {

    val response = MutableLiveData<LoginViewState>()

    private val disposable = CompositeDisposable()

    private val TAG = LoginViewModel::class.simpleName

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun checkEmailLogin(email: String): LoginViewState? {
        response.value = LoginViewState.ShowLoadingState
        if (Cache.apiToken.isNotEmpty()) {
            response.value = LoginViewState.ShowMainScreen
        } else {
            val loadUserEmail: Disposable = repository.loadUserEmail(email)
                .subscribe (
                    {
                        Cache.apiToken = it.user.token
                        response.value = LoginViewState.ShowMainScreen
                    },
                    {
                        showError(it)
                    }
                )
            disposable.add(loadUserEmail)
        }
        return response.value
    }

    private fun showError(error: Throwable) {
        when (error) {
            is NoNetworkException -> {
                response.value = LoginViewState.ShowRequestError(R.string.error_internet)
                Log.e(TAG,"Internet not available. ${error.message}")
            }
            is ServerUnreachableException -> {
                response.value = LoginViewState.ShowRequestError(R.string.error_request)
                Log.e(TAG,"Server is unreachable. ${error.message}")
            }
            is HttpCallFailureException -> {
                response.value = LoginViewState.ShowRequestError(R.string.error_request)
                Log.e(TAG,"Call failed. ${error.message}")
            }
            else -> {
                response.value = LoginViewState.ShowRequestError(R.string.error_email)
                Log.e(TAG,"Error: ${error.message}")
            }
        }
    }
}