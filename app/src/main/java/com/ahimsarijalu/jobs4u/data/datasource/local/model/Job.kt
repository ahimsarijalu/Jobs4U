package com.ahimsarijalu.jobs4u.data.datasource.local.model

import kotlinx.serialization.Serializable

@Serializable
data class Job(
    val jobId: String = "",
    val avatarUrl: String = "",
    val name: String = "",
    val username: String = "",
    val text: String = "",
    val saved: Boolean? = false,
    val allImage: List<String>? = listOf(),
)


