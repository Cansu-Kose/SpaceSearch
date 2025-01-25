package com.example.spacesearch.data.model.entity

import com.google.gson.annotations.SerializedName

data class RedditData(
    @SerializedName("after") val after : String?,
    @SerializedName("children") val children: List<RedditPost>
)