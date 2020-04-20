package com.rosseti.iddog.di

import dagger.Module

@Module(includes = [
    LoginViewModelModule::class,
    SchedulerModule::class,
    NetworkModule::class])
class AppModule