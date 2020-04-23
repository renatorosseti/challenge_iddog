package com.rosseti.iddog.login

import android.util.Log
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.rosseti.iddog.R
import com.rosseti.iddog.data.Cache
import com.rosseti.iddog.model.HttpCallFailureException
import com.rosseti.iddog.model.NoNetworkException
import com.rosseti.iddog.model.ServerUnreachableException
import com.rosseti.iddog.util.NetworkUtil

class LoginViewModel (
    private val repository: LoginRepository,
    private val networkUtil: NetworkUtil): ViewModel() {

    val response = MediatorLiveData<LoginViewState>()

    private val disposable = CompositeDisposable()

    private val TAG = LoginViewModel::class.simpleName

    init {
        response.addSource(networkUtil, Observer {
            if (!networkUtil.isInternetAvailable()) {
                response.value = LoginViewState.ShowRequestError(R.string.error_internet)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun checkEmailLogin(email: String): LoginViewState? {
        if (email.isEmpty()) {
            response.value = LoginViewState.ShowRequestError(R.string.error_email_empty)
            return response.value
        }

        if (!networkUtil.isInternetAvailable()) {
            response.value = LoginViewState.ShowRequestError(R.string.error_internet)
            return response.value
        }

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
                response.value = LoginViewState.ShowRequestError(R.string.error_email_format)
                Log.e(TAG,"Error: ${error.message}")
            }
        }
    }
}