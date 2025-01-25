package com.example.spacesearch.data.model.entity

import com.google.gson.annotations.SerializedName

data class RedditPost(
    @SerializedName("data") val postData: PostData
)