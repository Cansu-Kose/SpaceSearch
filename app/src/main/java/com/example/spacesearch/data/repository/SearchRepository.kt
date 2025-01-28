package com.example.spacesearch.data.repository

import com.example.spacesearch.data.common.DataState
import com.example.spacesearch.data.model.entity.PostsResult
import com.example.spacesearch.data.service.remote.SearchAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: SearchAPIService) : SearchRepositoryInterFace {

    override suspend fun getTopPosts(
        keyword: String,
        time: String,
        limit: Int,
        after: String
    ): Flow<DataState<PostsResult>> = flow {
        emit(DataState.Loading)
        try {
            val apiResponse = apiService.getTopPosts(keyword, rawJson = 1, time, limit, after)
            val posts = apiResponse.data.children.map { it.postData }
            val afterVal = apiResponse.data.after

            emit(DataState.Success(PostsResult(posts, afterVal)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}
