package com.example.spacesearch.data.repository

import com.example.spacesearch.data.common.DataState
import com.example.spacesearch.data.model.entity.PostsResult
import kotlinx.coroutines.flow.Flow

interface SearchRepositoryInterFace {
    suspend fun getTopPosts(keyword: String,time: String,limit:Int,after:String): Flow<DataState<PostsResult>>
}