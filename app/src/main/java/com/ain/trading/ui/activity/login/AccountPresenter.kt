package com.ain.trading.ui.activity.login

import com.ain.trading.R
import com.ain.trading.api.LoginResponse
import com.ain.trading.api.RegistrationResponse
import com.ain.trading.customviews.MyApplication
import com.ain.trading.utils.AinPref
import retrofit2.Call
import retrofit2.Response

class AccountPresenter(var registerView: AccountView?) {

    fun detach() {
        registerView = null
    }


    fun UserRegistration(
        first: String,
        last: String,
        mobile: String,
        email: String,
        password: String,
        confirm: String,
        user_type_id: String

    ) {
        registerView?.showProgress()


       MyApplication.networkService?.registration(first,last,mobile,email,password,confirm,user_type_id,AinPref.getLanguageId().toString())?.enqueue(object : retrofit2.Callback<RegistrationResponse> {

           override fun onFailure(call: Call<RegistrationResponse>?, t: Throwable?) {

               registerView?.hideProgress()

                  registerView?.onError(t?.message)

           }


           override fun onResponse(call: Call<RegistrationResponse>?, response: Response<RegistrationResponse>) {
               registerView?.hideProgress()
               if (response?.isSuccessful!!) {

                   registerView?.onRegistrationSuccess(response.body())


               } else {
                   registerView?.onError(MyApplication.instance?.getString(R.string.server_error))

               }

           }
       })
    }

    fun UserLogin(
        email: String,
        password: String,
        device_id:String
    ) {
        registerView?.showProgress()


        MyApplication.networkService?.login(email,password,device_id)?.enqueue(object : retrofit2.Callback<LoginResponse> {

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {

                registerView?.hideProgress()

                registerView?.onError(t?.message)

            }


            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>) {
                registerView?.hideProgress()
                if (response?.isSuccessful!!) {

                    registerView?.onLoginSuccess(response.body())


                } else {
                    registerView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }


}