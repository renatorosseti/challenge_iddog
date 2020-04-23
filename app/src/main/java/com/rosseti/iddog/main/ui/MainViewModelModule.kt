package com.rosseti.iddog.main.ui

import com.rosseti.iddog.util.NetworkUtil
import dagger.Module
import dagger.Provides

@Module
class MainViewModelModule {

    @Provides
    fun providesMainModelView(repository: MainRepository, networkUtil: NetworkUtil) = MainViewModel(repository, networkUtil)

}