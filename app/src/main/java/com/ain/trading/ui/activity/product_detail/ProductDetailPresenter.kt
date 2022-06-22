package com.ain.trading.ui.activity.product_detail

import com.ain.trading.R
import com.ain.trading.api.*

import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class ProductDetailPresenter (var productView: ProductView?) {

    fun detach() {
        productView = null
    }


    fun getProductDetail(productToken: String?) {
        productView?.showProgress()


        MyApplication.networkService?.getProductDetail(productToken.toString())?.enqueue(object : retrofit2.Callback<ProductDetails> {

            override fun onFailure(call: Call<ProductDetails>?, t: Throwable?) {

                productView?.hideProgress()

                productView?.onError(t?.message)

            }

            override fun onResponse(call: Call<ProductDetails>?, response: Response<ProductDetails>) {
                productView?.hideProgress()
                if(response?.isSuccessful!!){

                    productView?.OnProductDetailFetched(response.body())


                } else {
                    productView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun addToWishList(productId: String?) {
        productView?.showProgress()


        MyApplication.networkService?.addWishList("new",productId.toString())?.enqueue(object : retrofit2.Callback<AddWishList> {

            override fun onFailure(call: Call<AddWishList>?, t: Throwable?) {

                productView?.hideProgress()

                productView?.onError(t?.message)

            }

            override fun onResponse(
                call: Call<AddWishList>?,
                response: Response<AddWishList>
            ) {
                productView?.hideProgress()
                if (response?.isSuccessful) {

                    productView?.OnAddWishListSuccess(response.body())


                }

                else
                {
                    productView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })


    }

    fun searchpostal(postal_code: String?) {
        productView?.showProgress()


        MyApplication.networkService?.searchpostal(postal_code.toString())?.enqueue(object : retrofit2.Callback<SearchPostalResponse> {

            override fun onFailure(call: Call<SearchPostalResponse>?, t: Throwable?) {

                productView?.hideProgress()

                productView?.onError(t?.message)

            }

            override fun onResponse(
                call: Call<SearchPostalResponse>?,
                response: Response<SearchPostalResponse>
            ) {
                productView?.hideProgress()
                if (response?.isSuccessful) {

                    productView?.OnSearchPostalCodeSuccess(response.body()!!)
                }
                else
                {
                    productView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }
    fun addToCartList(productId: String?) {
        productView?.showProgress()


        MyApplication.networkService?.addCartList(productId.toString(),"1")?.enqueue(object : retrofit2.Callback<CartAddedResponse> {

            override fun onFailure(call: Call<CartAddedResponse>?, t: Throwable?) {

                productView?.hideProgress()

                productView?.onError(t?.message)

            }

            override fun onResponse(
                call: Call<CartAddedResponse>?,
                response: Response<CartAddedResponse>
            ) {
                productView?.hideProgress()
                if (response?.isSuccessful) {

                    productView?.OnAddCartListSuccess(response.body()!!)
                }
                else
                {
                    productView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun similarProducts(productId: String?, categoryId: String?) {
        productView?.showProgress()


        MyApplication.networkService?.getRelatedProducts(categoryId.toString(),productId.toString())?.enqueue(object : retrofit2.Callback<RelatedProducts> {

            override fun onFailure(call: Call<RelatedProducts>?, t: Throwable?) {

                productView?.hideProgress()

                productView?.onError(t?.message)

            }

            override fun onResponse(
                call: Call<RelatedProducts>?,
                response: Response<RelatedProducts>
            ) {
                productView?.hideProgress()
                if (response?.isSuccessful) {

                    productView?.OnRelatedProductListSuccess(response.body()!!)


                }
                else
                {
                    productView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }


}