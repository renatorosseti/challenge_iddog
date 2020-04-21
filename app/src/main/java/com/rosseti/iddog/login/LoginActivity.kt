package com.rosseti.iddog.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.rosseti.iddog.R
import com.rosseti.iddog.data.Cache
import com.rosseti.iddog.dialog.ErrorDialog
import com.rosseti.iddog.dialog.ProgressDialog
import com.rosseti.iddog.main.MainActivity
import com.rosseti.iddog.util.InternetUtil
import com.rosseti.iddog.util.InternetViewState
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var internetUtil: InternetUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        observeViewModel()
        observeInternetUtil()
    }

    override fun onResume() {
        super.onResume()
        checkApiTokenSession()
        handleSubmitEmailEventListener()
    }

    fun observeInternetUtil() {
        internetUtil.observe(this, Observer {
            when (it) {
                is InternetViewState.HasNoInternet -> {
                    ProgressDialog.hide()
                    ErrorDialog.show(context = this, message = getString(R.string.error_internet))
                }
            }
        })
    }

    fun observeViewModel() {
        viewModel.response.observe(this, Observer {
            ProgressDialog.hide()
            when (it) {
                is LoginViewState.ShowLoadingState -> ProgressDialog.show(context = this)
                is LoginViewState.ShowMainScreen -> navigateToMainScreen()
                is LoginViewState.ShowRequestError -> ErrorDialog.show(
                    context = this,
                    message = it.message
                )
            }
        })
    }

    fun checkApiTokenSession() {
        if (Cache.apiToken.isNotEmpty()) {
            navigateToMainScreen()
        }
    }

    fun handleSubmitEmailEventListener() {
        submitEmail.setOnClickListener {
            if (internetUtil.isInternetAvalable()) {
                val email: String = emailEditText.text.toString()
                viewModel.checkEmailLogin(email)
            } else {
                ErrorDialog.show(
                    context = this,
                    message = getString(R.string.error_internet))
            }
        }
    }

    fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
