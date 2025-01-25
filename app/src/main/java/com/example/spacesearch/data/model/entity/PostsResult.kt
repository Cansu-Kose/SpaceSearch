package com.example.spacesearch.data.model.entity

data class PostsResult(
    val posts: List<PostData>,
    val after: String?
)