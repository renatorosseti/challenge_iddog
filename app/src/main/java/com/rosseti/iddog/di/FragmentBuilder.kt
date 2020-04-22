package com.rosseti.iddog.di

import com.rosseti.iddog.main.ui.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = [AppModule::class])
    abstract fun contributeMainFragment(): MainFragment
}




