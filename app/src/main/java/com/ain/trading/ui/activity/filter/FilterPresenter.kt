package com.ain.trading.ui.activity.filter

import com.ain.trading.R

import com.ain.trading.api.FilterResponse
import com.ain.trading.api.ParentProductList
import com.ain.trading.customviews.MyApplication
import retrofit2.Call
import retrofit2.Response

class FilterPresenter (var filterView: FilterView?) {
    var bodyValue: FilterResponse? = null
    fun detach() {
        filterView = null
    }

    fun getFilter() {
        filterView?.showProgress()
        MyApplication.networkService?.getFilter()?.enqueue(object : retrofit2.Callback<FilterResponse> {
            override fun onFailure(call: Call<FilterResponse>, t: Throwable) {
                filterView?.hideProgress()

                filterView?.onError(t?.message)
            }

            override fun onResponse(call: Call<FilterResponse>, response: Response<FilterResponse>) {
                filterView?.hideProgress()
                if (response?.isSuccessful) {

                    filterView?.onFilterFetched(response.body())
                    //filterView?.onFilterItemsFetched(response.body()?.data)
                     bodyValue=response.body()
                    /*for (i in 0 until bodyValue?.data?.size!!)
                    {
                        var filterId = bodyValue.data.get(i).filter_id
                    }*/


                } else {
                    filterView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }

    fun getfilterItems(filterId: String) {

       // var value =bodyValue!!.data.get().filter_id.


            for (i in 0 until bodyValue!!.data.size) {
                if (filterId == bodyValue!!.data.get(i).filter_id.toString()) {
                    filterView?.onFilterItemsFetched(bodyValue!!.data.get(i).childs)
                }
            }
    }

   /* fun getfilterItems(bodyValue: FilterResponse) {
        filterView?.showProgress()

        MyApplication.networkService?.getFilterItems(filterId)?.enqueue(object : retrofit2.Callback<FilterResponse> {
            override fun onFailure(call: Call<FilterResponse>, t: Throwable) {
                filterView?.hideProgress()

                filterView?.onError(t?.message)
            }

            override fun onResponse(call: Call<FilterResponse>, response: Response<FilterResponse>) {
                filterView?.hideProgress()
                if (response?.isSuccessful) {

                    filterView?.onFilterItemsFetched(response.body())

                } else {

                    filterView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }*/


    fun getFilterProducts(filter_id: String?) {
        filterView?.showProgress()
        MyApplication.networkService?.getFilterProducts(filter_id)?.enqueue(object : retrofit2.Callback<ParentProductList> {
            override fun onFailure(call: Call<ParentProductList>, t: Throwable) {
                filterView?.hideProgress()

                filterView?.onError(t?.message)
            }

            override fun onResponse(call: Call<ParentProductList>, response: Response<ParentProductList>) {
                filterView?.hideProgress()
                if(response?.isSuccessful!!){

                    filterView?.onFilterProductsFetched(response.body())


                } else {
                    filterView?.onError(MyApplication.instance?.getString(R.string.server_error))

                }
            }


        })
    }






}