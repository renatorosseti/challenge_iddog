package com.rosseti.iddog.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.rosseti.iddog.R
import com.rosseti.iddog.base.BaseActivity
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

    fun handleSubmitEmailEventListener() {
        submitEmail.setOnClickListener {
            val email: String = emailEditText.text.toString()
            viewModel.checkEmailLogin(email)
        }
    }

    fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
