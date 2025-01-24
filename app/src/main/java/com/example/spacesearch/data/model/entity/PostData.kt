package com.example.spacesearch.data.model.entity

data class PostData(
    val subreddit_name_prefixed: String,
    val title: String,
    val selftext: String,
    val url: String,
    val created_utc: Double
)