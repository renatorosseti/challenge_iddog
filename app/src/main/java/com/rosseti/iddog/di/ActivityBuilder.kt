package com.rosseti.iddog.di

import com.rosseti.iddog.login.LoginActivity
import com.rosseti.iddog.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [AppModule::class])
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [AppModule::class])
    abstract fun contributeMainActivity(): MainActivity
}




