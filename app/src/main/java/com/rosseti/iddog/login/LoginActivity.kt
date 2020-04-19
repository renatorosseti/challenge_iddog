package com.rosseti.iddog.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.rosseti.iddog.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val viewModel = LoginViewModel(LoginRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel.response.observe(this, Observer {
            Log.i("MainActivity",  "Status: $it.")
            submitEmail.text = it.input
        })
    }

    override fun onResume() {
        super.onResume()
        submitEmail.setOnClickListener {
            viewModel.checkEmailLogin()
        }
    }
}
