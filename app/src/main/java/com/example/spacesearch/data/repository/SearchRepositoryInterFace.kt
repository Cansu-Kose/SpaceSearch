package com.example.spacesearch.data.repository

import com.example.spacesearch.data.common.DataState
import com.example.spacesearch.data.model.entity.PostData
import kotlinx.coroutines.flow.Flow

interface SearchRepositoryInterFace {
    suspend fun getTopPosts(keyword: String,time: String,limit:Int): Flow<DataState<List<PostData>>>
}