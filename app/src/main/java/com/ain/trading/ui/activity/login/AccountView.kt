package com.ain.trading.ui.activity.login

import com.ain.trading.api.LoginResponse
import com.ain.trading.api.RegistrationResponse
import com.ain.trading.customviews.CommonView

interface AccountView:CommonView {
    fun onRegistrationSuccess(body: RegistrationResponse?)
    fun onLoginSuccess(body: LoginResponse?)
}