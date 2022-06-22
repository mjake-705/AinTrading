package com.ain.trading.ui.activity.home

import com.ain.trading.R
import com.ain.trading.api.*

import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class HomePresenter(var homeview: HomeView?) {


    fun getCateGories() {

        homeview?.showProgress()


        MyApplication.networkService?.getCategoryList()?.enqueue(object : retrofit2.Callback<Maincategory> {

            override fun onFailure(call: Call<Maincategory>?, t: Throwable?) {

                homeview?.hideProgress()

                homeview?.onError(t?.message)

            }

            override fun onResponse(call: Call<Maincategory>?, response: Response<Maincategory>) {
                homeview?.hideProgress()
                if(response?.isSuccessful!!){

                    homeview?.onCategoryFetched(response.body())


                } else {
                    homeview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })

    }

    fun getBanners() {
        homeview?.showProgress()


        MyApplication.networkService?.getBanners("10")?.enqueue(object : retrofit2.Callback<Banners> {

            override fun onFailure(call: Call<Banners>?, t: Throwable?) {

                homeview?.hideProgress()

                homeview?.onError(t?.message)

            }

            override fun onResponse(call: Call<Banners>?, response: Response<Banners>) {
                homeview?.hideProgress()
                if(response?.isSuccessful!!){

                    homeview?.onBannersFetched(response.body())


                } else {
                    homeview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun getbrandinFocus() {
        homeview?.showProgress()


        MyApplication.networkService?.getbrandinFocus("29")?.enqueue(object : retrofit2.Callback<Banners> {

            override fun onFailure(call: Call<Banners>?, t: Throwable?) {

                homeview?.hideProgress()

                homeview?.onError(t?.message)

            }

            override fun onResponse(call: Call<Banners>?, response: Response<Banners>) {
                homeview?.hideProgress()
                if(response?.isSuccessful!!){

                    homeview?.onBrandinFocusfetched(response.body())



                } else {
                    homeview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun getLatestProduct() {
        homeview?.showProgress()


        MyApplication.networkService?.getLatestProduct("latest")?.enqueue(object : retrofit2.Callback<ParentProductList> {

            override fun onFailure(call: Call<ParentProductList>?, t: Throwable?) {

                homeview?.hideProgress()

                homeview?.onError(t?.message)

            }

            override fun onResponse(call: Call<ParentProductList>?, response: Response<ParentProductList>) {
                homeview?.hideProgress()
                if(response?.isSuccessful!!){

                    homeview?.onLatestProductfetched(response.body())


                } else {
                    homeview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun addToCartList(productId: String?) {
        homeview?.showProgress()


        MyApplication.networkService?.addCartList(productId.toString(),"1")?.enqueue(object : retrofit2.Callback<CartAddedResponse> {

            override fun onFailure(call: Call<CartAddedResponse>?, t: Throwable?) {

                homeview?.hideProgress()

                homeview?.onError(t?.message)

            }

            override fun onResponse(
                call: Call<CartAddedResponse>?,
                response: Response<CartAddedResponse>
            ) {
                homeview?.hideProgress()
                if (response?.isSuccessful) {

                    homeview?.OnAddCartListSuccess(response.body()!!)


                }

                else
                {
                    homeview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }
    fun getCategoryDetail(category_id: String?) {
        homeview?.showProgress()


        MyApplication.networkService?.getCategoryDetails(category_id.toString())?.enqueue(object : retrofit2.Callback<SubCategoryParent> {

            override fun onFailure(call: Call<SubCategoryParent>?, t: Throwable?) {

                homeview?.hideProgress()

                homeview?.onError(t?.message)

            }

            override fun onResponse(call: Call<SubCategoryParent>?, response: Response<SubCategoryParent>) {
                homeview?.hideProgress()
                if(response?.isSuccessful!!){

                    homeview?.onCategoryDetailFetched(response.body())


                } else {
                    homeview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun getSubcategoryDetaiil(category_id: String?) {
        homeview?.showProgress()


        MyApplication.networkService?.getSubCategoryDetails(category_id.toString())?.enqueue(object : retrofit2.Callback<SubcategoryParentDetail> {

            override fun onFailure(call: Call<SubcategoryParentDetail>?, t: Throwable?) {

                homeview?.hideProgress()

                homeview?.onError(t?.message)

            }

            override fun onResponse(call: Call<SubcategoryParentDetail>?, response: Response<SubcategoryParentDetail>) {
                homeview?.hideProgress()
                if(response?.isSuccessful!!){

                    homeview?.onSubCategoryDetailFetched(response.body())


                } else {
                    homeview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun getProducts(category_id: String?) {
        homeview?.showProgress()


        MyApplication.networkService?.getParentProduct(category_id.toString())?.enqueue(object : retrofit2.Callback<ParentProductList> {

            override fun onFailure(call: Call<ParentProductList>?, t: Throwable?) {

                homeview?.hideProgress()

                homeview?.onError(t?.message)

            }

            override fun onResponse(call: Call<ParentProductList>?, response: Response<ParentProductList>) {
                homeview?.hideProgress()
                if(response?.isSuccessful!!){

                    homeview?.onSubCategoryProductsFetched(response.body())


                } else {
                    homeview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun getBrands() {
        homeview?.showProgress()


        MyApplication.networkService?.getBrands()?.enqueue(object : retrofit2.Callback<BrandsList> {

            override fun onFailure(call: Call<BrandsList>?, t: Throwable?) {

                homeview?.hideProgress()

                homeview?.onError(t?.message)

            }

            override fun onResponse(call: Call<BrandsList>?, response: Response<BrandsList>) {
                homeview?.hideProgress()
                if(response?.isSuccessful!!){

                    homeview?.onBrandsFetched(response.body())


                } else {
                    homeview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun getBrandsDetails(brandId: String?) {
        homeview?.showProgress()


        MyApplication.networkService?.getBrandsDetailsList(brandId.toString())?.enqueue(object : retrofit2.Callback<BrandDatailList> {

            override fun onFailure(call: Call<BrandDatailList>?, t: Throwable?) {

                homeview?.hideProgress()

                homeview?.onError(t?.message)

            }

            override fun onResponse(call: Call<BrandDatailList>?, response: Response<BrandDatailList>) {
                homeview?.hideProgress()
                if(response?.isSuccessful!!){

                    homeview?.onBrandsListFetched(response.body())


                } else {
                    homeview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }

    fun getCategoryBanner() {
        homeview?.showProgress()


        MyApplication.networkService?.getCategoryBanner("11")?.enqueue(object : retrofit2.Callback<Banners> {

            override fun onFailure(call: Call<Banners>?, t: Throwable?) {

                homeview?.hideProgress()

                homeview?.onError(t?.message)

            }

            override fun onResponse(call: Call<Banners>?, response: Response<Banners>) {
                homeview?.hideProgress()
                if(response?.isSuccessful!!){

                    homeview?.onCategoryBannerFetched(response.body())


                } else {
                    homeview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }
}