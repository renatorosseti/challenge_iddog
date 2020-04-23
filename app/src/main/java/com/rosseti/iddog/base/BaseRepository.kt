package com.rosseti.iddog.base

import com.bumptech.glide.load.HttpException
import com.rosseti.iddog.model.DomainMappable
import com.rosseti.iddog.model.HttpCallFailureException
import com.rosseti.iddog.model.NoNetworkException
import com.rosseti.iddog.model.ServerUnreachableException
import io.reactivex.Single
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {

    protected fun <T : DomainMappable<R>, R> Single<T>.mapToDomain(): Single<R> =
        this.map {
            it.asDomain()
        }

    protected fun <R> mapNetworkErrors() = {
            single: Single<R> ->
        single.onErrorResumeNext {
                error -> when (error) {
            is SocketTimeoutException -> Single.error(NoNetworkException(error))
            is UnknownHostException -> Single.error(ServerUnreachableException(error))
            is HttpException -> Single.error(HttpCallFailureException(error))
            else -> Single.error(error)
        }
        }
    }
}