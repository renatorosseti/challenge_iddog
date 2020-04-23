package com.rosseti.iddog.base

import androidx.lifecycle.Observer
import com.rosseti.iddog.R
import com.rosseti.iddog.dialog.ErrorDialog
import com.rosseti.iddog.dialog.ProgressDialog
import com.rosseti.iddog.util.InternetUtil
import com.rosseti.iddog.util.InternetViewState
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var errorDialog: ErrorDialog

    @Inject
    lateinit var progressDialog: ProgressDialog

    @Inject
    lateinit var internetUtil: InternetUtil

    override fun onResume() {
        super.onResume()
        observeInternetUtil()
    }

    private fun observeInternetUtil() {
        internetUtil.observe(this, Observer {
            when (it) {
                is InternetViewState.HasNoInternet -> {
                    progressDialog.hide()
                    errorDialog.show(context = this, message = getString(R.string.error_internet))
                }
            }
        })
    }
}