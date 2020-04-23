package com.rosseti.iddog.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.rosseti.iddog.R
import com.rosseti.iddog.base.BaseActivity
import com.rosseti.iddog.data.Cache
import com.rosseti.iddog.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
        checkApiTokenSession()
        handleSubmitEmailEventListener()
    }

    fun observeViewModel() {
        viewModel.response.observe(this, Observer {
            progressDialog.hide()
            when (it) {
                is LoginViewState.ShowLoadingState -> progressDialog.show(context = this)
                is LoginViewState.ShowMainScreen -> {
                    progressDialog.hide()
                    navigateToMainScreen()
                }
                is LoginViewState.ShowRequestError -> {
                    progressDialog.hide()
                    errorDialog.show(
                        context = this,
                        message = getString(it.message)
                    )
                }
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
            if (internetUtil.isInternetAvailable()) {
                val email: String = emailEditText.text.toString()
                viewModel.checkEmailLogin(email)
            } else {
                errorDialog.show(
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
