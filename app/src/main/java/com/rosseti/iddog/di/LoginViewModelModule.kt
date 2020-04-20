package com.rosseti.iddog.di

import com.rosseti.iddog.login.LoginRepository
import com.rosseti.iddog.login.LoginViewModel
import dagger.Module
import dagger.Provides

@Module
class LoginViewModelModule {

    @Provides
    fun providesHomeModelView(repository: LoginRepository) = LoginViewModel(repository)
}