package com.ahimsarijalu.jobs4u.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahimsarijalu.jobs4u.ui.home.HomeViewModel

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(Injection.provideHomeRepository()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}