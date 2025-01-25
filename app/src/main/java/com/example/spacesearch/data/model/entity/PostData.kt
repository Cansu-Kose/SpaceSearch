package com.example.spacesearch.data.model.entity

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("subreddit_name_prefixed") val subredditNamePrefixed: String,
    @SerializedName("title") val title: String,
    @SerializedName("selftext") val selfText: String,
    @SerializedName("thumbnail") val url: String,
    @SerializedName("created_utc") val createdUtc: Double
)