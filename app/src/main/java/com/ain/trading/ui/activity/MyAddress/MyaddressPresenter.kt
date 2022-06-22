package com.ain.trading.ui.activity.MyAddress

import com.ain.trading.R
import com.ain.trading.api.DeleteAddressResponse
import com.ain.trading.api.ListAddressResponse
import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class MyaddressPresenter (var myaddressView:MyAddressView?){


    fun getAddressList() {

        myaddressView?.showProgress()


        MyApplication.networkService?.getAddressList()?.enqueue(object : retrofit2.Callback<ListAddressResponse> {

            override fun onFailure(call: Call<ListAddressResponse>?, t: Throwable?) {

                myaddressView?.hideProgress()

                myaddressView?.onError(t?.message)

            }

            override fun onResponse(call: Call<ListAddressResponse>?, response: Response<ListAddressResponse>) {
                myaddressView?.hideProgress()
                if(response?.isSuccessful!!){

                    myaddressView?.onAddressListFetched(response.body())


                } else {
                    myaddressView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })

    }

    fun deleteAddress(
        customer_to_address_id:String
    ) {

        myaddressView?.showProgress()


        MyApplication.networkService?.deleteAddress("delete",customer_to_address_id)?.enqueue(object : retrofit2.Callback<DeleteAddressResponse> {

            override fun onFailure(call: Call<DeleteAddressResponse>?, t: Throwable?) {

                myaddressView?.hideProgress()

                myaddressView?.onError(t?.message)

            }

            override fun onResponse(call: Call<DeleteAddressResponse>?, response: Response<DeleteAddressResponse>) {
                myaddressView?.hideProgress()
                if(response?.isSuccessful!!){

                    myaddressView?.onDeletedAddressSuccessfully(response.body())


                } else {
                    myaddressView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })

    }






}