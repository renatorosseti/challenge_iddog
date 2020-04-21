package com.rosseti.iddog.di

import com.rosseti.iddog.IddogApp
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuilder::class])
interface AppComponent : AndroidInjector<IddogApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<IddogApp>()
}