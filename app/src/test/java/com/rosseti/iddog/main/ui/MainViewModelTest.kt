package com.rosseti.iddog.main.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rosseti.iddog.R
import com.rosseti.iddog.data.Cache
import com.rosseti.iddog.model.FeedResponse
import com.rosseti.iddog.model.NetworkException
import com.rosseti.iddog.util.NetworkUtil
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

    @MockK
    lateinit var networkUtil: NetworkUtil

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(mainRepository, networkUtil)
    }

    @Test
    fun fetchFeedContent_whenInternetIsNotAvailable() {
        val category = "Husky"
        every { networkUtil.isInternetAvailable() } returns false

        val response = viewModel.fetchFeedContent(category)
        Truth.assertThat(response).isEqualTo(MainViewState.ShowRequestError(R.string.error_internet))
    }

    @Test
    fun fetchFeedContent_whenHasContentFeedCached() {
        val category = "Husky"
        var list: List<String> = listOf("Images")
        Cache.contentFeed[category] = list
        every { networkUtil.isInternetAvailable() } returns true

        val response = viewModel.fetchFeedContent(category)
        Truth.assertThat(response).isEqualTo(MainViewState.ShowContentFeed(list))
    }

    @Test
    fun fetchFeedContent_whenSucceed() {
        val category = "Husky"
        val apiToken = "Correct api token"
        var list: List<String> = listOf("Images")
        Cache.apiToken = apiToken
        every { networkUtil.isInternetAvailable() } returns true
        every { mainRepository.loadFeed(category, apiToken) } returns Single.just(FeedResponse(list = list, category = category))

        val response = viewModel.fetchFeedContent(category)
        Truth.assertThat(response).isEqualTo(MainViewState.ShowContentFeed(list))
    }

    @Test(expected = NetworkException::class)
    fun fetchFeedContent_whenExceptionOccurs() {
        var category = "husky"
        var apiToken = "Wrong api token"
        Cache.apiToken = apiToken
        every { networkUtil.isInternetAvailable() } returns true
        every { mainRepository.loadFeed(category = category, apiToken = apiToken) } throws NetworkException(Throwable())

        val response = viewModel.fetchFeedContent(category)
        Truth.assertThat(response).isEqualTo(MainViewState.ShowRequestError(R.string.error_request))
    }
}