package com.rosseti.iddog.main.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rosseti.iddog.R
import com.rosseti.iddog.data.Cache
import com.rosseti.iddog.model.HttpCallFailureException
import com.rosseti.iddog.model.NoNetworkException
import com.rosseti.iddog.model.ServerUnreachableException
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainViewModel(
    private val repository: MainRepository): ViewModel() {

    val response = MutableLiveData<MainViewState>()

    private val disposable = CompositeDisposable()

    private val TAG = MainViewModel::class.simpleName


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun fetchFeedContent(category: String): MainViewState? {
        response.value = MainViewState.ShowLoadingState
        val cachedList = Cache.contentFeed[category]
        if (cachedList != null && cachedList.isNotEmpty()) {
            response.value = MainViewState.ShowContentFeed(cachedList!!)
        } else {
            val loadFeedContent: Disposable = repository.loadFeed(category, Cache.apiToken)
                .subscribe (
                    {
                        response.value = MainViewState.ShowContentFeed(it.list)
                        Cache.contentFeed[category] = it.list
                    },
                    {
                        response.value = MainViewState.ShowRequestError(R.string.error_request)
                        showError(it)
                    }
                )
            disposable.add(loadFeedContent)
        }
        return response.value
    }

    private fun showError(error: Throwable) {
        when (error) {
            is NoNetworkException -> Log.e(TAG,"Internet not available. ${error.message}")
            is ServerUnreachableException -> Log.e(TAG,"Server is unreachable. ${error.message}")
            is HttpCallFailureException -> Log.e(TAG,"Call failed. ${error.message}")
            else -> Log.e(TAG,"Error: ${error.message}.")
        }
    }
}