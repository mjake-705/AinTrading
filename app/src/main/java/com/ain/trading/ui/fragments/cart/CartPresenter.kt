package com.ain.trading.ui.fragments.cart

import com.ain.trading.R
import com.ain.trading.api.*
import com.ain.trading.customviews.MyApplication

import retrofit2.Call
import retrofit2.Response

class CartPresenter (var cartView: CartView?) {
    fun detach() {
        cartView= null
    }

    fun getCart() {
        cartView?.showProgress()
        MyApplication.networkService?.getCart()?.enqueue(object : retrofit2.Callback<GetCartlistResponse> {
            override fun onFailure(call: Call<GetCartlistResponse>, t: Throwable) {
                cartView?.hideProgress()

                cartView?.onError(t?.message)
            }

            override fun onResponse(call: Call<GetCartlistResponse>, response: Response<GetCartlistResponse>) {
                cartView?.hideProgress()
                if(response?.isSuccessful!!){

                    cartView!!.onCartFetched(response.body())


                } else {
                    cartView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }

    fun removeCartitem(cartId: String) {
        cartView?.showProgress()
        MyApplication.networkService?.removeCart(cartId)?.enqueue(object : retrofit2.Callback<CartRemoveResponse> {
            override fun onFailure(call: Call<CartRemoveResponse>, t: Throwable) {
                cartView?.hideProgress()

                cartView?.onError(t?.message)
            }

            override fun onResponse(call: Call<CartRemoveResponse>, response: Response<CartRemoveResponse>) {
                cartView?.hideProgress()
                if(response?.isSuccessful!!){

                    cartView!!.onCartRemovedSuccesfully(response.body())


                } else {
                    cartView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }

    fun updateCart(editCount: String, productId: Int) {
        cartView?.showProgress()
        MyApplication.networkService?.updateCart(productId.toString(),editCount)?.enqueue(object : retrofit2.Callback<UpdatedCart> {
            override fun onFailure(call: Call<UpdatedCart>, t: Throwable) {
                cartView?.hideProgress()

                cartView?.onError(t?.message)
            }

            override fun onResponse(call: Call<UpdatedCart>, response: Response<UpdatedCart>) {
                cartView?.hideProgress()
                if(response?.isSuccessful!!){

                    cartView?.onCartUpdatedSuccesfully(response.body())


                } else {
                    cartView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }


    fun getQuantityData(data: String) {

        cartView?.showProgress()


        MyApplication.networkService?.getQuantityData(data)?.enqueue(object : retrofit2.Callback<QuantityResponse> {

            override fun onFailure(call: Call<QuantityResponse>?, t: Throwable?) {

                cartView?.hideProgress()

                cartView?.onError(t?.message)

            }

            override fun onResponse(call: Call<QuantityResponse>?, response: Response<QuantityResponse>) {
                cartView?.hideProgress()
                if(response?.isSuccessful!!){

                  //  cartView?.onQuantityFetched(response.body()!!.data)


                } else {
                    cartView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }



    fun addToWishList(productId: String?) {
        cartView?.showProgress()


        MyApplication.networkService?.addWishList("new",productId.toString())?.enqueue(object : retrofit2.Callback<AddWishList> {

            override fun onFailure(call: Call<AddWishList>?, t: Throwable?) {

                cartView?.hideProgress()

                cartView?.onError(t?.message)

            }

            override fun onResponse(
                call: Call<AddWishList>?,
                response: Response<AddWishList>
            ) {
                cartView?.hideProgress()
                if (response?.isSuccessful) {

                    cartView?.OnAddWishListSuccess(response.body())


                }

                else
                {
                    cartView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })


    }





}