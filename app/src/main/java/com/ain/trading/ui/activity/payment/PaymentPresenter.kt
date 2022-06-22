package com.ain.trading.ui.activity.payment

import com.ain.trading.R
import com.ain.trading.api.PaymentResponse
import com.ain.trading.api.PaymentTypeResponse
import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class PaymentPresenter (var paymentView: PaymentView?) {

    fun getPayment(customer_to_address_id: String, payment_type: String, shipping_rate: String?) {
        paymentView?.showProgress()
        MyApplication.networkService?.onPayment(customer_to_address_id,payment_type,shipping_rate.toString())?.enqueue(object : retrofit2.Callback<PaymentResponse> {
            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                paymentView?.hideProgress()

                paymentView?.onError(t?.message)
            }

            override fun onResponse(call: Call<PaymentResponse>, response: Response<PaymentResponse>) {
                paymentView?.hideProgress()
                if(response?.isSuccessful!!){

                    paymentView?.onPaySuccesfully(response.body())


                } else {
                    paymentView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }





    fun getpaymentType(){
        paymentView?.showProgress()
        MyApplication.networkService?.getpaymentType()?.enqueue(object : retrofit2.Callback<PaymentTypeResponse> {
            override fun onFailure(call: Call<PaymentTypeResponse>, t: Throwable) {
                paymentView?.hideProgress()

                paymentView?.onError(t?.message)
            }

            override fun onResponse(call: Call<PaymentTypeResponse>, response: Response<PaymentTypeResponse>) {
                paymentView?.hideProgress()
                if(response?.isSuccessful!!){

                    paymentView?.onPaymentTypeFetched(response.body())


                } else {
                    paymentView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }



}