package com.ahimsarijalu.jobs4u.data.datasource.local.model

data class Jobs(
    val avatar_url: String,
    val name: String,
    val username: String,
    val content: String,
    val imageUrls: List<String>? = listOf<String>()
)
