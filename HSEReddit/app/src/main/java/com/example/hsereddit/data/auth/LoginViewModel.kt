package com.example.hsereddit.data.auth

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hsereddit.data.model.Result

import com.example.hsereddit.R
import com.example.hsereddit.data.model.AuthorizedData
import com.example.hsereddit.data.model.LoginFormState
import com.example.hsereddit.data.model.LoginResult
import com.example.hsereddit.network.Requests
import com.example.hsereddit.network.RestApiService
import java.io.IOException

class LoginViewModel() : ViewModel() {

    var api: RestApiService = RestApiService()
//    var authorizedUserResult: Result<AuthorizedData>? = null

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
//        val result = loginRepository.login(username, password)
        try {
            if (username.isEmpty() or password.isEmpty()) {
                throw IllegalArgumentException("Invalid username or password")
            }
            api.login(
                Requests.LoginRequest(
                    password,
                    username.split('@')[0]
                )
            ) {
                val authorizedUserResult: Result<AuthorizedData> = if (it == null) {
                    Result.Error(IllegalArgumentException("EMPTY RESULT"))
                } else {
//                    storeAuthData(it)
                    Result.Success(it)
                }

                if (authorizedUserResult is Result.Success) {
                    _loginResult.value = LoginResult(success = authorizedUserResult.data)
                } else {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            }
        } catch (e: Throwable) {
            Log.i("ERROR", e.message.toString())
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

//    fun storeAuthData(data: AuthorizedData) {
//        val bundle = Bundle()
//        bundle.putString("username", data.username)
//        bundle.putString("authToken", data.authenticationToken)
//        bundle.putString("refreshToken", data.refreshToken)
//        bundle.putString("expiresAt", data.expiresAt.toString())
//    }
}