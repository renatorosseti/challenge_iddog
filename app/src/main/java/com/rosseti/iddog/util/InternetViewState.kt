package com.rosseti.iddog.util

sealed class InternetViewState {
    object HasInternet: InternetViewState()
    object HasNoInternet: InternetViewState()
}


