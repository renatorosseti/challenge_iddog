package com.rosseti.iddog.model

interface DomainMappable<R> {
    fun asDomain(): R
}