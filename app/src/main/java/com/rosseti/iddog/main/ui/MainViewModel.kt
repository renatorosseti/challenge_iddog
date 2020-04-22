package com.rosseti.iddog.main.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rosseti.iddog.data.Cache
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainViewModel(
    private val repository: MainRepository): ViewModel() {

    val response = MutableLiveData<MainViewState>()

    private val disposable = CompositeDisposable()

    private val TAG = MainViewModel::class.simpleName

    var test = "Main"

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun fetchFeedContent() {
        response.value = MainViewState.ShowLoadingState
        val loadUserEmail: Disposable = repository.loadFeed(Cache.apiToken)
            .subscribe (
                {
                    Log.i("MainViewModel", "Response: ${it.category}")
                    Log.i("MainViewModel", "Response: ${it.list}")
                },
                {
                    response.value = MainViewState.ShowRequestError("Error: Email not valid.")
                    Log.e(TAG,"Error: ${it.message}")
                }
            )
        disposable.add(loadUserEmail)
    }
}