//package com.example.hsereddit.data.auth
//
//import android.os.Bundle
//import com.example.hsereddit.data.model.AuthorizedData
//import com.example.hsereddit.data.model.Result
//
///**
// * Class that requests authentication and user information from the remote data source and
// * maintains an in-memory cache of login status and user credentials information.
// */
//
//class LoginRepository(val dataSource: LoginDataSource) {
//    var user: AuthorizedData? = null
//        private set
//
//    val isLoggedIn: Boolean
//        get() = user != null
//
//    var isSignedUp: Boolean = false
//
//
//    fun logout() {
//        user = null
//    }
//
////    fun login(username: String, password: String): Result<AuthorizedData> {
////        val result = dataSource.login(username, password)
////
////        if (result is Result.Success) {
////            setLoggedInUser(result.data)
////        }
////
////        return result
////    }
//
//    fun registration(username: String, password: String): Result<Boolean> {
//        val result = dataSource.registration(username, password)
//
//        if (result is Result.Success) {
//            isSignedUp = true
//        }
//
//        return result
//    }
//
//    private fun setLoggedInUser(authorizedData: AuthorizedData) {
//        this.user = authorizedData
//        val bundle = Bundle()
//        bundle.putString("username", authorizedData.username)
//        bundle.putString("authToken", authorizedData.authenticationToken)
//        bundle.putString("refreshToken", authorizedData.refreshToken)
//        bundle.putString("expiresAt", authorizedData.expiresAt.toString())
//    }
//}