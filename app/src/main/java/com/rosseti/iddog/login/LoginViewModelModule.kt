package com.rosseti.iddog.login

import com.rosseti.iddog.IddogApp
import com.rosseti.iddog.login.LoginRepository
import com.rosseti.iddog.login.LoginViewModel
import com.rosseti.iddog.util.InternetUtil
import dagger.Module
import dagger.Provides

@Module
class LoginViewModelModule {

    @Provides
    fun providesHomeModelView(repository: LoginRepository) = LoginViewModel(repository)

    @Provides
    fun providesInternetUtil(application: IddogApp) = InternetUtil(application)
}