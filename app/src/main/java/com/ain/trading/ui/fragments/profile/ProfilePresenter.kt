package com.ain.trading.ui.fragments.profile

import com.ain.trading.R
import com.ain.trading.api.LanguageUpdate
import com.ain.trading.api.ProfileDetails
import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class ProfilePresenter (var profileView: ProfileView?) {

    fun detach() {
        profileView = null
    }


    fun getProfile() {
        profileView?.showProgress()


        MyApplication.networkService?.getProfileDetails()?.enqueue(object : retrofit2.Callback<ProfileDetails> {

            override fun onFailure(call: Call<ProfileDetails>?, t: Throwable?) {

                profileView?.hideProgress()

                profileView?.onError(t?.message)

            }

            override fun onResponse(call: Call<ProfileDetails>?, response: Response<ProfileDetails>) {
                profileView?.hideProgress()
                if(response?.isSuccessful!!){

                    profileView?.OnProfileDetailFetched(response.body())


                } else {
                    profileView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun updateProfile(first: String, last: String, gender: String) {
        profileView?.showProgress()


        MyApplication.networkService?.updateProfileDetails(first,last,gender)?.enqueue(object : retrofit2.Callback<ProfileDetails> {

            override fun onFailure(call: Call<ProfileDetails>?, t: Throwable?) {

                profileView?.hideProgress()

                profileView?.onError(t?.message)

            }

            override fun onResponse(call: Call<ProfileDetails>?, response: Response<ProfileDetails>) {
                profileView?.hideProgress()
                if(response?.isSuccessful!!){

                    profileView?.OnProfileUpdateSuccess(response.body())


                } else {
                    profileView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun updateLanguage(languageId: String?) {
        profileView?.showProgress()


        MyApplication.networkService?.updateProfilLanguage(languageId.toString())?.enqueue(object : retrofit2.Callback<LanguageUpdate> {

            override fun onFailure(call: Call<LanguageUpdate>?, t: Throwable?) {

                profileView?.hideProgress()

                profileView?.onError(t?.message)

            }

            override fun onResponse(call: Call<LanguageUpdate>?, response: Response<LanguageUpdate>) {
                profileView?.hideProgress()
                if(response?.isSuccessful!!){

                    profileView?.OnProfileLanguageUpdateSuccess(response.body())


                } else {
                    profileView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }


}