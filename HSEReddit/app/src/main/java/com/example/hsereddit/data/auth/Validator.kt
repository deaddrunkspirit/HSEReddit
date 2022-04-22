package com.example.hsereddit.data.auth


object Validator {
    private val hsePattern: Regex = Regex("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@(edu\\.hse|hse)\\.ru")
    fun validatePassword(password: String?): Boolean {
        if (password!!.length < 8) return false
        if (!password.contains(Regex("[A-ZА-Я]"))) return false
        if (!password.contains(Regex("[a-zа-я]"))) return false
        if (!password.contains(Regex("[0-9]"))) return false
        return true
    }

    fun validateLogin(login: String?): Boolean {
        return hsePattern.matches(login!!)
    }
}