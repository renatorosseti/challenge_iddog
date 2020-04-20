package com.rosseti.iddog.data

object Cache {

    var token = ""

    fun cacheApiToken(token: String) {
        this.token = token
    }
}
