package com.rosseti.iddog.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rosseti.iddog.R
import com.rosseti.iddog.model.NetworkException
import com.rosseti.iddog.model.SignUpResponse
import com.rosseti.iddog.model.User
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @MockK
    lateinit var loginRepository: LoginRepository


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = LoginViewModel(loginRepository)
    }

    @Test(expected = NetworkException::class)
    fun checkEmailLogin_whenExceptionOccurs() {
        val email = "Email test"
        every { loginRepository.loadUserEmail(email) } throws NetworkException(Throwable())
        viewModel.checkEmailLogin(email)
        Truth.assertThat(viewModel.response.value).isEqualTo(LoginViewState.ShowRequestError(R.string.error_email))
    }

    @Test
    fun checkEmailLogin_whenSucceed() {
        val email = "Email test"
        val token = "Token test"
        every { loginRepository.loadUserEmail(email) } returns Single.just(SignUpResponse(user = User(token = token)))
        viewModel.checkEmailLogin(email)
        Truth.assertThat(viewModel.response.value).isEqualTo(LoginViewState.ShowMainScreen)
    }
}