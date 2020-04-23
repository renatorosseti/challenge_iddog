package com.rosseti.iddog.di

import com.rosseti.iddog.login.LoginViewModelModule
import dagger.Module

@Module(includes = [
    LoginViewModelModule::class,
    SchedulerModule::class,
    NetworkModule::class,
    DialogModule::class])
class AppModule