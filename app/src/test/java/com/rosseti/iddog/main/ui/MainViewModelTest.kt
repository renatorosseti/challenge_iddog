package com.rosseti.iddog.main.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rosseti.iddog.R
import com.rosseti.iddog.data.Cache
import com.rosseti.iddog.model.FeedResponse
import com.rosseti.iddog.model.NetworkException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @MockK
    lateinit var mainRepository: MainRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(mainRepository)
    }

    @Test(expected = NetworkException::class)
    fun fetchFeedContent_whenExceptionOccurs() {
        var category = "husky"
        var apiToken = "Wrong api token"
        Cache.apiToken = apiToken

        every { mainRepository.loadFeed(category = category, apiToken = apiToken) } throws NetworkException(Throwable())
        viewModel.fetchFeedContent(category)
        Truth.assertThat(viewModel.response.value).isEqualTo(MainViewState.ShowRequestError(R.string.error_request))
    }

    @Test
    fun fetchFeedContent_whenSucceed() {
        val category = "Husky"
        val apiToken = "Correct api token"
        var list: List<String> = listOf("Images")
        Cache.apiToken = apiToken

        every { mainRepository.loadFeed(category, apiToken) } returns Single.just(FeedResponse(list = list, category = category))
        viewModel.fetchFeedContent(category)
        Truth.assertThat(viewModel.response.value).isEqualTo(MainViewState.ShowContentFeed(list))
    }
}