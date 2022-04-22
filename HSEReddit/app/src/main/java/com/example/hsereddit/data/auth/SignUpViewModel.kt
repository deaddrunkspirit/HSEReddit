package com.example.hsereddit.data.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hsereddit.R
import com.example.hsereddit.data.model.Result
import com.example.hsereddit.data.model.SignUpFormState
import com.example.hsereddit.data.model.SignUpResult

class SignUpViewModel(): ViewModel() {

    private val _signUpForm = MutableLiveData<SignUpFormState>()
    val signUpFormState: LiveData<SignUpFormState> = _signUpForm

    private val _signUpResult = MutableLiveData<SignUpResult>()
    val signUpResult: LiveData<SignUpResult> = _signUpResult

    fun registration(username: String, password1: String, password2: String) {
//        signUpDataChanged(password1, password1, password2)
//        if (signUpFormState.value?.isDataValid!!) {
//            val result = loginRepository.registration(username, password1)
//            if (result is Result.Success) {
//                _signUpResult.value =
//                    SignUpResult(
//                        success = true
//                    )
//            } else {
//                _signUpResult.value = SignUpResult(error = R.string.login_failed)
//            }
//        } else _signUpResult.value = SignUpResult(error = R.string.login_failed)
    }

    private fun signUpDataChanged(username: String, password1: String, password2: String) {
        if (!isUserNameValid(username)) {
            _signUpForm.value = SignUpFormState(usernameError = R.string.invalid_username)
        } else if (!isPassword1Valid(password1)) {
            _signUpForm.value = SignUpFormState(password1Error = R.string.invalid_password)
        } else if (!isPassword2Valid(password1, password2)) {
            _signUpForm.value = SignUpFormState(password2Error = R.string.invalid_password)
        } else {
            _signUpForm.value = SignUpFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return Validator.validateLogin(username)
    }

    private fun isPassword1Valid(password: String): Boolean {
        return Validator.validatePassword(password)
    }

    private fun isPassword2Valid(password1: String, password2: String): Boolean {
        return password1 == password2
    }
}