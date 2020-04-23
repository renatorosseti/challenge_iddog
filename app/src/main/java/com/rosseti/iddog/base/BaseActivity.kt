package com.rosseti.iddog.base

import com.rosseti.iddog.dialog.ErrorDialog
import com.rosseti.iddog.dialog.ProgressDialog
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var errorDialog: ErrorDialog

    @Inject
    lateinit var progressDialog: ProgressDialog

}