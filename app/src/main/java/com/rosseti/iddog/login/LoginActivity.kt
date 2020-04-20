package com.rosseti.iddog.login

import android.os.Bundle
import androidx.lifecycle.Observer
import com.rosseti.iddog.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel.response.observe(this, Observer {
            submitEmail.text = it.input
        })
    }

    override fun onResume() {
        super.onResume()
        val email: String = emailEditText.text.toString()
        submitEmail.setOnClickListener {
            viewModel.checkEmailLogin(email)
        }
    }
}
