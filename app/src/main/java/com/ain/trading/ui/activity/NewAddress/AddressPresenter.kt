package com.ain.trading.ui.activity.NewAddress

import com.ain.trading.R
import com.ain.trading.api.*
import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class AddressPresenter(var addressView: AddressView?) {

    fun detach() {
        addressView= null
    }
    fun getRegion(country_id: String?){
        addressView?.showProgress()
        MyApplication.networkService?.getRegion("101")?.enqueue(object : retrofit2.Callback<StateResponse> {
            override fun onFailure(call: Call<StateResponse>, t: Throwable) {
                addressView?.hideProgress()

                addressView?.onError(t?.message)
            }

            override fun onResponse(call: Call<StateResponse>, response: Response<StateResponse>) {
                addressView?.hideProgress()
                if(response?.isSuccessful!!){

                    addressView?.onCountryRegionFetched(response.body())


                } else {
                    addressView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }

    fun getSubRegion(state_id: String?){
        addressView?.showProgress()

        MyApplication.networkService?.getSubRegion(state_id)?.enqueue(object : retrofit2.Callback<CountryCity> {
            override fun onFailure(call: Call<CountryCity>, t: Throwable) {
                addressView?.hideProgress()

                addressView?.onError(t?.message)
            }

            override fun onResponse(call: Call<CountryCity>, response: Response<CountryCity>) {
                addressView?.hideProgress()
                if(response?.isSuccessful!!){

                    addressView?.onCountryCityFetched(response.body())


                } else {
                    addressView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }







    fun addressAdding(
        address_holder: String,
        primary_number: String,
        primary_address:String,
        address_type_id: String,
        location: String,
        postal_code:String,
        city_name:String,
        default:String

    ) {
        addressView?.showProgress()


        MyApplication.networkService?.addressAdding("new",address_holder,primary_number,primary_address,address_type_id,location,postal_code,city_name,default)?.enqueue(object : retrofit2.Callback<AddAddress> {

            override fun onFailure(call: Call<AddAddress>?, t: Throwable?) {

                addressView?.hideProgress()

                addressView?.onError(t?.message)

            }


            override fun onResponse(call: Call<AddAddress>?, response: Response<AddAddress>) {
                addressView?.hideProgress()
                if (response?.isSuccessful!!) {

                    addressView?.onAddressAddingSuccesss(response.body())


                } else {
                    addressView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

}