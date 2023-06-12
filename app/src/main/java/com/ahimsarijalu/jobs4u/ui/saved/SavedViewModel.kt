package com.ahimsarijalu.jobs4u.ui.saved

import androidx.lifecycle.ViewModel
import com.ahimsarijalu.jobs4u.data.datasource.local.model.Job
import com.ahimsarijalu.jobs4u.data.repository.JobsRepository

class SavedViewModel(private val jobsRepository: JobsRepository) : ViewModel() {
   fun getSavedJob() = jobsRepository.getAllSaved()

   fun saveJob(job: Job) = jobsRepository.saveJob(job)
   fun removeSavedJob(job: Job) = jobsRepository.removeSavedJob(job)
}