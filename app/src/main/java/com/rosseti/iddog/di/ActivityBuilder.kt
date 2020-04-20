package com.rosseti.iddog.di

import com.rosseti.iddog.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [AppModule::class])
    abstract fun contributeLoginctivity(): LoginActivity
}