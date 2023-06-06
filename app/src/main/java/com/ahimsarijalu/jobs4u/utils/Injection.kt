package com.ahimsarijalu.jobs4u.utils

import com.ahimsarijalu.jobs4u.data.repository.HomeRepository
import com.ahimsarijalu.jobs4u.data.repository.UserRepository

object Injection {
    fun provideHomeRepository(): HomeRepository {
        return HomeRepository()
    }

    fun provideUserRepository(): UserRepository {
        return UserRepository()
    }
}