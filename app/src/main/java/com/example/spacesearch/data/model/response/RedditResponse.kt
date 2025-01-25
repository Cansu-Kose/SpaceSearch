package com.example.spacesearch.data.model.response

import com.example.spacesearch.data.model.entity.RedditData
import com.google.gson.annotations.SerializedName

data class RedditResponse(
    @SerializedName("data") val data: RedditData
)