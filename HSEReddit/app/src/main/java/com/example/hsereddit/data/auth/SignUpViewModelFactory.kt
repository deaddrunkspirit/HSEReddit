package com.example.hsereddit.data.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SignUpViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(
//                loginRepository = LoginRepository(
//                    dataSource = LoginDataSource()
//                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}