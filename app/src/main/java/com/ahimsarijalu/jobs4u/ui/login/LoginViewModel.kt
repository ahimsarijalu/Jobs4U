package com.ahimsarijalu.jobs4u.ui.login

import androidx.lifecycle.ViewModel
import com.ahimsarijalu.jobs4u.data.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {
    fun login(email: String, password: String) = userRepository.login(email, password)
}