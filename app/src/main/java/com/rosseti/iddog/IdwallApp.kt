package com.rosseti.iddog

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.rosseti.iddog.di.DaggerAppComponent

class IdwallApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out IdwallApp> = DaggerAppComponent.builder().create(this)

}