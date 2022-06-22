package com.ain.trading.ui.fragments.wishlist

import com.ain.trading.R
import com.ain.trading.api.ProfileDetails
import com.ain.trading.api.RemoveWishlistNew
import com.ain.trading.api.WishListNew
import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class WishlistPresenter (var wishlistView: WishlistView?) {

    fun detach() {
        wishlistView = null
    }


    fun getWishList() {
        wishlistView?.showProgress()


        MyApplication.networkService?.getWishList()?.enqueue(object : retrofit2.Callback<WishListNew> {

                override fun onFailure(call: Call<WishListNew>?, t: Throwable?) {

                    wishlistView?.hideProgress()
                    wishlistView?.onError(t?.message)

                }

                override fun onResponse(
                    call: Call<WishListNew>?,
                    response: Response<WishListNew>
                ) {
                    wishlistView?.hideProgress()
                    if (response?.isSuccessful!!) {

                        wishlistView!!.OnWishListFetched(response.body())


                    } else {
                        wishlistView?.onError(MyApplication.instance?.getString(R.string.server_error))

                    }

                }
            })
    }


    fun removeWishList(wishlist_id: String) {
        MyApplication.networkService?.removeWishList(wishlist_id)?.enqueue(object : retrofit2.Callback<RemoveWishlistNew> {

            override fun onFailure(call: Call<RemoveWishlistNew>?, t: Throwable?) {

                wishlistView?.hideProgress()

                wishlistView?.onError(t?.message)

            }

            override fun onResponse(call: Call<RemoveWishlistNew>?, response: Response<RemoveWishlistNew>) {
                wishlistView?.hideProgress()
                if(response?.isSuccessful!!){

                    wishlistView!!.onWishlistRemoveSuccess(response.body())


                } else {
                    wishlistView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }



}