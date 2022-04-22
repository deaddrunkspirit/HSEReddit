package com.example.hsereddit.data.auth

import android.util.Log
import com.example.hsereddit.data.model.AuthorizedData
import com.example.hsereddit.data.model.Result
import com.example.hsereddit.network.Requests
import com.example.hsereddit.network.RestApiService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.lang.Error

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    var api: RestApiService = RestApiService()
//    var authorizedUserResult: Result<AuthorizedData>? = null
//    fun login(username: String, password: String): Result<AuthorizedData> {
//        try {
//            if (username.isEmpty() or password.isEmpty()) {
//                throw IllegalArgumentException("Invalid username or password")
//            }
//            api.login(
//                Requests.LoginRequest(
//                    password,
//                    username.split('@')[0]
//                )
//            ) {
//                if (it == null) {
//                    authorizedUserResult = Result.Error(IllegalArgumentException("EMPTY RESULT"))
//                } else {
//                    authorizedUserResult = Result.Success(it)
//                }
//            }
//        } catch (e: Throwable) {
//            Log.i("ERROR", e.message.toString())
//            return Result.Error(IOException("Error logging in", e))
//        }
//    }

    fun registration(username: String, password: String): Result<Boolean> {
        return try {
            api.signup(Requests.SignupRequest(username, password)) { }
            Result.Success(true)
        } catch (e: Throwable) {
            Result.Error(IOException("Error signing up", e))
        }
    }
}