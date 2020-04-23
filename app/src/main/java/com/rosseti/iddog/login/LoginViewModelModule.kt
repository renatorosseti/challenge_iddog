package com.rosseti.iddog.login

import com.rosseti.iddog.util.NetworkUtil
import dagger.Module
import dagger.Provides

@Module
class LoginViewModelModule {

    @Provides
    fun providesHomeModelView(repository: LoginRepository, networkUtil: NetworkUtil) = LoginViewModel(repository,networkUtil)
}