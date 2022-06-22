package com.ain.trading.ui.activity.otp

import com.ain.trading.api.OTPResponse
import com.ain.trading.customviews.CommonView

interface OTP_View:CommonView {
    fun onOTPSuccesfully(body: OTPResponse?)

}