package com.ain.trading.ui.activity.AddressDetail

import com.ain.trading.R
import com.ain.trading.api.DefaultAddressResponse
import com.ain.trading.api.GetCartlistResponse
import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class AddressDetailActivityPresenter(var mAddressDetailActivityView: AddressDetailView?) {

    fun defaultAddress() {
        mAddressDetailActivityView?.showProgress()


        MyApplication.networkService?.defaultAddress("default")?.enqueue(object : retrofit2.Callback<DefaultAddressResponse> {

            override fun onFailure(call: Call<DefaultAddressResponse>?, t: Throwable?) {

                mAddressDetailActivityView?.hideProgress()

                mAddressDetailActivityView?.onError(t?.message)

            }


            override fun onResponse(call: Call<DefaultAddressResponse>?, response: Response<DefaultAddressResponse>) {
                mAddressDetailActivityView?.hideProgress()
                if (response?.isSuccessful!!) {

                    mAddressDetailActivityView?.showdefaultAddressSuccesss(response.body())


                } else {
                    mAddressDetailActivityView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun getCart() {
        mAddressDetailActivityView?.showProgress()
        MyApplication.networkService?.getCart()?.enqueue(object : retrofit2.Callback<GetCartlistResponse> {
            override fun onFailure(call: Call<GetCartlistResponse>, t: Throwable) {
                mAddressDetailActivityView?.hideProgress()

                mAddressDetailActivityView?.onError(t?.message)
            }

            override fun onResponse(call: Call<GetCartlistResponse>, response: Response<GetCartlistResponse>) {
                mAddressDetailActivityView?.hideProgress()
                if(response?.isSuccessful!!){

                    mAddressDetailActivityView!!.onCartFetched(response.body())


                } else {
                    mAddressDetailActivityView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }



}