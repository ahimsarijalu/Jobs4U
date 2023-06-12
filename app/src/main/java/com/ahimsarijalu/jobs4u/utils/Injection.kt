package com.ahimsarijalu.jobs4u.utils

import com.ahimsarijalu.jobs4u.data.repository.JobsRepository
import com.ahimsarijalu.jobs4u.data.repository.UserRepository

object Injection {
    fun provideJobsRepository(): JobsRepository {
        return JobsRepository()
    }

    fun provideUserRepository(): UserRepository {
        return UserRepository()
    }
}