package com.ahimsarijalu.jobs4u.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahimsarijalu.jobs4u.ui.changePassword.ChangePasswordViewModel
import com.ahimsarijalu.jobs4u.ui.home.HomeViewModel
import com.ahimsarijalu.jobs4u.ui.login.LoginViewModel
import com.ahimsarijalu.jobs4u.ui.profile.ProfileViewModel
import com.ahimsarijalu.jobs4u.ui.register.RegisterViewModel

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(Injection.provideHomeRepository()) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(Injection.provideUserRepository()) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideUserRepository()) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(Injection.provideUserRepository()) as T
            }

            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> {
                ChangePasswordViewModel(Injection.provideUserRepository()) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}