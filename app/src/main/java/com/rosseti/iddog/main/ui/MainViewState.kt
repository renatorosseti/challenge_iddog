package com.rosseti.iddog.main.ui

sealed class MainViewState {
    object ShowLoadingState: MainViewState()
    data class ShowContentFeed(val images: List<String>): MainViewState()
    data class ShowRequestError(val message: Int): MainViewState()
}


