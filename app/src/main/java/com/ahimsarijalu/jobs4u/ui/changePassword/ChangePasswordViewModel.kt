package com.ahimsarijalu.jobs4u.ui.changePassword

import androidx.lifecycle.ViewModel
import com.ahimsarijalu.jobs4u.data.repository.UserRepository

class ChangePasswordViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun changePassword(oldPassword: String, password: String) = userRepository.changePassword(oldPassword, password)
}