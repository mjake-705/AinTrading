package com.ain.trading.ui.activity.changepassword

import com.ain.trading.api.DeleteAddressResponse
import com.ain.trading.api.PasswordChange
import com.ain.trading.customviews.CommonView

interface ChangePasswordView:CommonView {
   fun onPasswordChangeSuccess(body: PasswordChange?)
    fun onForgotPasswordSuccess(body: DeleteAddressResponse?)

}