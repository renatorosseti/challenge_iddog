package com.rosseti.iddog.main.ui

import dagger.Module
import dagger.Provides

@Module
class MainViewModelModule {

    @Provides
    fun providesMainModelView(repository: MainRepository) = MainViewModel(repository)

}