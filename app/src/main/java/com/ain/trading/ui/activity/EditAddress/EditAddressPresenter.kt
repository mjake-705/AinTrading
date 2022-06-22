package com.ain.trading.ui.activity.EditAddress

import com.ain.trading.R
import com.ain.trading.api.EditAddressResponse
import com.ain.trading.api.FindAddressResponse
import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response


class EditAddressPresenter(var editaddressView: EditAddressView?) {

    fun editaddresslist(
        address_holder: String,
        primary_number: String,
        primary_address:String,
        address_type_id: String,
        location: String,
        postal_code:String,
        city_id:String,
        default:String,
        customer_to_address_id:String
    ) {
        editaddressView?.showProgress()


        MyApplication.networkService?.editaddresslist("edit",address_holder,primary_number,primary_address,address_type_id,location,postal_code,city_id,default,customer_to_address_id)?.enqueue(object : retrofit2.Callback<EditAddressResponse> {

            override fun onFailure(call: Call<EditAddressResponse>?, t: Throwable?) {

                editaddressView?.hideProgress()

                editaddressView?.onError(t?.message)

            }


            override fun onResponse(call: Call<EditAddressResponse>?, response: Response<EditAddressResponse>) {
                editaddressView?.hideProgress()
                if (response?.isSuccessful!!) {

                    editaddressView?.onEditAddressSuccesss(response.body())


                } else {
                    editaddressView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }


    fun setdefaultAddress(
        primary_number:String,
        default:String,
        customer_to_address_id:String
    ) {
        editaddressView?.showProgress()


        MyApplication.networkService?.setdefaultAddress("edit",primary_number,default,customer_to_address_id)?.enqueue(object : retrofit2.Callback<EditAddressResponse> {

            override fun onFailure(call: Call<EditAddressResponse>?, t: Throwable?) {

                editaddressView?.hideProgress()

                editaddressView?.onError(t?.message)

            }


            override fun onResponse(call: Call<EditAddressResponse>?, response: Response<EditAddressResponse>) {
                editaddressView?.hideProgress()
                if (response?.isSuccessful!!) {

                    editaddressView!!.setDefaulttAddressSuccesss(response.body())


                } else {
                    editaddressView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }


    fun findparticularAddress(

        customer_to_address_id: String?
    ) {
        editaddressView?.showProgress()


        MyApplication.networkService?.findparticularAddress("find",customer_to_address_id)?.enqueue(object : retrofit2.Callback<FindAddressResponse> {

            override fun onFailure(call: Call<FindAddressResponse>?, t: Throwable?) {

                editaddressView?.hideProgress()

                editaddressView?.onError(t?.message)

            }


            override fun onResponse(call: Call<FindAddressResponse>?, response: Response<FindAddressResponse>) {
                editaddressView?.hideProgress()
                if (response?.isSuccessful!!) {

                    editaddressView!!.setFindAddressSuccesss(response.body())


                } else {
                    editaddressView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }





}