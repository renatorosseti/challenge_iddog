package com.rosseti.iddog.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rosseti.iddog.R
import com.rosseti.iddog.data.Cache
import com.rosseti.iddog.model.NetworkException
import com.rosseti.iddog.model.SignUpResponse
import com.rosseti.iddog.model.User
import com.rosseti.iddog.util.NetworkUtil
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

    @MockK
    lateinit var networkUtil: NetworkUtil

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = LoginViewModel(loginRepository, networkUtil)
    }

    @Test
    fun checkEmailLogin_whenEmailFieldIsEmpty() {
        val email = ""
        every { networkUtil.isInternetAvailable() } returns true

        val response = viewModel.checkEmailLogin(email)
        Truth.assertThat(response).isEqualTo(LoginViewState.ShowRequestError(R.string.error_email_empty))
    }

    @Test
    fun checkEmailLogin_whenHasTokenCached() {
        val email = "Email test"
        val token = "Token test"
        Cache.apiToken = token
        every { networkUtil.isInternetAvailable() } returns true

        val response: LoginViewState? = viewModel.checkEmailLogin(email)
        Truth.assertThat(response).isEqualTo(LoginViewState.ShowMainScreen)
    }

    @Test
    fun checkEmailLogin_whenRequestNetworkSucceed() {
        val email = "Email test"
        val token = "Token test"
        every { networkUtil.isInternetAvailable() } returns true
        every { loginRepository.loadUserEmail(email) } returns Single.just(SignUpResponse(user = User(token = token)))

        val response: LoginViewState? = viewModel.checkEmailLogin(email)
        Truth.assertThat(response).isEqualTo(LoginViewState.ShowMainScreen)
    }

    @Test(expected = NetworkException::class)
    fun checkEmailLogin_whenEmailEmailIsNotValid() {
        val email = "email"
        every { networkUtil.isInternetAvailable() } returns true
        every { viewModel.checkEmailLogin(email) } throws NetworkException(Throwable())

        val response = viewModel.checkEmailLogin(email)
        Truth.assertThat(response).isEqualTo(LoginViewState.ShowRequestError(R.string.error_email_format))
    }
}