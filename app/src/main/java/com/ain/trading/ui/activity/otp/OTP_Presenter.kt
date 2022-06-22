package com.ain.trading.ui.activity.otp

import com.ain.trading.R
import com.ain.trading.api.OTPResponse
import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class OTP_Presenter(var otpView: OTP_View?) {

    fun getOTP(number: String, otp: String,id:String) {
        otpView?.showProgress()
        MyApplication.networkService?.getOTP(number,otp,id)?.enqueue(object : retrofit2.Callback<OTPResponse> {
            override fun onFailure(call: Call<OTPResponse>, t: Throwable) {
                otpView?.hideProgress()

                otpView?.onError(t?.message)
            }

            override fun onResponse(call: Call<OTPResponse>, response: Response<OTPResponse>) {
                otpView?.hideProgress()
                if(response?.isSuccessful!!){

                    otpView?.onOTPSuccesfully(response.body())


                } else {
                    otpView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }



}