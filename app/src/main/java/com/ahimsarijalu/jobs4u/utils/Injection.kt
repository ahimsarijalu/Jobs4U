package com.ahimsarijalu.jobs4u.utils

import com.ahimsarijalu.jobs4u.data.repository.HomeRepository

object Injection {
    fun provideHomeRepository(): HomeRepository {
        return HomeRepository()
    }
}