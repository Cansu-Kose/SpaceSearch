package com.example.spacesearch.data.service.remote

import com.example.spacesearch.data.model.response.RedditResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchAPIService {

    @GET("{keyword}/top.json")
    suspend fun getTopPosts(@Path("keyword") keyword: String, @Query("raw_json") rawJson: Int = 1, @Query("t") time: String, @Query("limit") limit: Int): RedditResponse

}