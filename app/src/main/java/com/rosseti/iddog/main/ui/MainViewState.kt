package com.rosseti.iddog.main.ui

sealed class MainViewState {
    object ShowLoadingState: MainViewState()
    object ShowMainScreen: MainViewState()
    data class ShowRequestError(val message: String): MainViewState()
}


