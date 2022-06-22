package com.ain.trading.ui.activity.orders

import com.ain.trading.R
import com.ain.trading.api.OrderlistResponse
import com.ain.trading.api.Orders
import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class OrdersPresenter(var ordersView: OrdersView?) {
    fun detach() {
        ordersView= null
    }
    fun getOrders(){
        ordersView?.showProgress()
        MyApplication.networkService?.getOrders()?.enqueue(object : retrofit2.Callback<Orders> {
            override fun onFailure(call: Call<Orders>, t: Throwable) {
                ordersView?.hideProgress()

                ordersView?.onError(t?.message)
            }

            override fun onResponse(call: Call<Orders>, response: Response<Orders>) {
                ordersView?.hideProgress()
                if(response?.isSuccessful!!){

                    ordersView?.onOrdersFetched(response.body())


                } else {
                    ordersView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }

    fun getOrderProducts(orderId: String?) {
        ordersView?.showProgress()
        MyApplication.networkService?.getOrderedProducts(orderId)?.enqueue(object : retrofit2.Callback<OrderlistResponse> {
            override fun onFailure(call: Call<OrderlistResponse>, t: Throwable) {
                ordersView?.hideProgress()

                ordersView?.onError(t?.message)
            }

            override fun onResponse(call: Call<OrderlistResponse>, response: Response<OrderlistResponse>) {
                ordersView?.hideProgress()
                if(response?.isSuccessful!!){

                    ordersView?.onOrdersFetchedItemFetched(response.body())


                } else {
                    ordersView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }
}