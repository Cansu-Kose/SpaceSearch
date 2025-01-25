package com.example.spacesearch.data.repository

import com.example.spacesearch.data.common.DataState
import com.example.spacesearch.data.model.entity.PostData
import com.example.spacesearch.data.service.remote.SearchAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: SearchAPIService):SearchRepositoryInterFace {

    override suspend fun getTopPosts(
        keyword: String,
        time: String,
        limit: Int
    ): Flow<DataState<List<PostData>>>  = flow {
        emit(DataState.Loading)
        try {
            val apiResponse = apiService.getTopPosts(keyword,1,time,limit)
            val posts = apiResponse.data.children.map { it.postData }
            emit(DataState.Success(posts))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}