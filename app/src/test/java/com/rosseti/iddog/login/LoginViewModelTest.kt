package com.rosseti.iddog.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rosseti.iddog.model.Error
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

    @Test
    fun checkEmailLogin() {
        var email = ""
        var error = "Error test"
        every { loginRepository.loadUserEmail(email) } returns Single.just(SignUpResponse(error = Error(message = error), user = User(token = "")))
        viewModel.checkEmailLogin(email)
        Truth.assertThat(viewModel.response.value).isEqualTo(LoginViewState.ShowRequestError(error))

        email = "test@test.com"
        error = ""
        every { loginRepository.loadUserEmail(email) } returns Single.just(SignUpResponse(error = Error(message = error), user = User(token = "12")))
        viewModel.checkEmailLogin(email)
        Truth.assertThat(viewModel.response.value).isEqualTo(LoginViewState.ShowMainScreen)
    }
}