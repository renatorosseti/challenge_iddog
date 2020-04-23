package com.rosseti.iddog.di

import com.rosseti.iddog.dialog.ErrorDialog
import com.rosseti.iddog.dialog.ProgressDialog
import dagger.Module
import dagger.Provides

@Module
class DialogModule {

    @Provides
    fun providesErrorDialog(): ErrorDialog = ErrorDialog()

    @Provides
    fun providesProgressDialog(): ProgressDialog = ProgressDialog()
}