package com.rosseti.iddog.login

sealed class LoginViewState {
    object ShowLoadingState: LoginViewState()
    object ShowMainScreen: LoginViewState()
    data class ShowRequestError(val message: String): LoginViewState()
}


