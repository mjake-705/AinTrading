package com.ain.trading.utils

object AinUtil {
    fun isValidMobile(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }
    fun isEmailValid(email: String): Boolean {
        return  android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}