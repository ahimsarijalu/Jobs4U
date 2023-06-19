package com.ahimsarijalu.jobs4u.ui.home

import androidx.lifecycle.ViewModel
import com.ahimsarijalu.jobs4u.data.datasource.local.model.Job
import com.ahimsarijalu.jobs4u.data.repository.JobsRepository

class HomeViewModel(
    private val jobsRepository: JobsRepository,
) : ViewModel() {
    fun getAllJobs() = jobsRepository.getAllJobs()

    fun search(keyword: String) = jobsRepository.search(keyword)

    fun filter(keyword: String) = jobsRepository.filter(keyword)

    fun saveJob(job: Job) = jobsRepository.saveJob(job)
    fun removeSavedJob(job: Job) = jobsRepository.removeSavedJob(job)
}