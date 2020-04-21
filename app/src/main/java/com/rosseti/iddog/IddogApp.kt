package com.rosseti.iddog

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.rosseti.iddog.di.DaggerAppComponent

class IddogApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out IddogApp> = DaggerAppComponent.builder().create(this)

}