package com.ain.trading.ui.activity.changepassword

import com.ain.trading.R
import com.ain.trading.api.DeleteAddressResponse
import com.ain.trading.api.PasswordChange
import com.ain.trading.api.StateResponse
import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class ChangePasswordPresenter(var changePasswordView: ChangePasswordView?) {
    fun detach() {
        changePasswordView= null
    }
    fun ChangePassword(
        oldpass: String,
        newpass: String,
        confirmpass: String
    ) {
        changePasswordView?.showProgress()
        MyApplication.networkService?.changePassword(oldpass,newpass,confirmpass)?.enqueue(object : retrofit2.Callback<PasswordChange> {
            override fun onFailure(call: Call<PasswordChange>, t: Throwable) {
                changePasswordView?.hideProgress()

                changePasswordView?.onError(t?.message)
            }

            override fun onResponse(call: Call<PasswordChange>, response: Response<PasswordChange>) {
                changePasswordView?.hideProgress()
                if(response?.isSuccessful!!){

                    changePasswordView?.onPasswordChangeSuccess(response.body())


                } else {
                    changePasswordView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }

    fun ForgotPassword(email: String) {
        MyApplication.networkService?.forgotPassword(email)?.enqueue(object : retrofit2.Callback<DeleteAddressResponse> {
            override fun onFailure(call: Call<DeleteAddressResponse>, t: Throwable) {
                changePasswordView?.hideProgress()

                changePasswordView?.onError(t?.message)
            }

            override fun onResponse(call: Call<DeleteAddressResponse>, response: Response<DeleteAddressResponse>) {
                changePasswordView?.hideProgress()
                if(response?.isSuccessful!!){

                    changePasswordView?.onForgotPasswordSuccess(response.body())


                } else {
                    changePasswordView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }
}