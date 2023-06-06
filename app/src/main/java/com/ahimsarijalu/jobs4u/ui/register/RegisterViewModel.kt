package com.ahimsarijalu.jobs4u.ui.register

import androidx.lifecycle.ViewModel
import com.ahimsarijalu.jobs4u.data.repository.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun registerUser(email: String, password: String, name: String) =
        userRepository.register(email, password, name)
}