package com.ahimsarijalu.jobs4u.data.datasource.local.model


data class JobSeeker(
    val id: String? = "",
    val name: String? = "",
    val email: String? = "",
    val listSavedJobs: List<Jobs>? = listOf(),
)
