package com.example.spacesearch.hilt

import app.cash.turbine.test
import com.example.spacesearch.data.common.DataState
import com.example.spacesearch.data.model.entity.PostData
import com.example.spacesearch.data.model.entity.PostsResult
import com.example.spacesearch.data.repository.SearchRepository
import com.example.spacesearch.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val mockRepo: SearchRepository = mockk()

    @Before
    fun setUp() {
        viewModel = MainViewModel(mockRepo)
    }

    @Test
    fun searchShouldUpdateSearchResultsOnSuccess() = runTest {
        val keyword = "kotlin"
        val time = "day"
        val limit = 20
        val afterToken = "token123"

        val mockPosts = listOf(
            PostData("r/Kotlin", "Kotlin Coroutines", "url1", 1633036800.0),
            PostData("r/Kotlin", "Jetpack Compose", "url2", 1633123200.0)
        )
        val postsResult = PostsResult(posts = mockPosts, after = afterToken)

        coEvery { mockRepo.getTopPosts(keyword, time, limit, "null") } returns flowOf(
            DataState.Loading,
            DataState.Success(postsResult)
        )

        viewModel.search(keyword, time, limit)

        viewModel.searchResults.test {
            val emission1 = awaitItem()
            assertEquals(emptyList<PostData>(), emission1) // İlk başta boş

            val emission2 = awaitItem()
            assertEquals(mockPosts, emission2)

            cancelAndIgnoreRemainingEvents()
        }

        coVerify (exactly = 1) { mockRepo.getTopPosts(keyword, time, limit, "null") }
    }

    @Test
    fun searchShouldHandleErrorState() = runTest {
        coEvery {
            mockRepo.getTopPosts("test", "all", 20, "null")
        } returns flow {
            emit(DataState.Error(Exception("Network error")))
        }

        viewModel.search("test", "all")

        viewModel.isLoading.test {
            assertEquals(false, awaitItem())
        }
    }
}

