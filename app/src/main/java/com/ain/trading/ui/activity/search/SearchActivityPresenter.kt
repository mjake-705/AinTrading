package com.ain.trading.ui.activity.search
import retrofit2.Call
import retrofit2.Response
import com.ain.trading.R
import com.ain.trading.api.SearchResponse
import com.ain.trading.customviews.MyApplication

class SearchActivityPresenter(var searchview: SearchView?) {

    fun getSearchData(data: String) {
        searchview?.showProgress()


        MyApplication.networkService?.getSearchData(data)?.enqueue(object : retrofit2.Callback<SearchResponse> {

            override fun onFailure(call: Call<SearchResponse>?, t: Throwable?) {

                searchview?.hideProgress()

                searchview?.onError(t?.message)

            }

            override fun onResponse(call: Call<SearchResponse>?, response: Response<SearchResponse>) {
                searchview?.hideProgress()
                if(response?.isSuccessful!!){

                    searchview?.onSearchDataFetched(response.body())


                } else {
                    searchview?.onError(MyApplication.instance?.getString(R.string.server_error))

                }

            }
        })
    }


}