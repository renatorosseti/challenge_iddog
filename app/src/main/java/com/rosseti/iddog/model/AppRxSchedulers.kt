package com.rosseti.iddog.model

import io.reactivex.Scheduler

data class AppRxSchedulers(
        val database: Scheduler,
        val disk: Scheduler,
        val network: Scheduler,
        val main: Scheduler
)