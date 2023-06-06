package com.ahimsarijalu.jobs4u.ui.profile

import androidx.lifecycle.ViewModel
import com.ahimsarijalu.jobs4u.data.repository.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUser() = userRepository.getUser()

    fun logout() = userRepository.logout()
}