package com.ain.trading.ui.activity.filter

import com.ain.trading.api.Child

import com.ain.trading.api.FilterResponse
import com.ain.trading.api.ParentProductList
import com.ain.trading.customviews.CommonView

interface FilterView : CommonView{
    fun onFilterFetched(body: FilterResponse?)
     fun onFilterItemsFetched(body: List<Child>)
    fun onFilterProductsFetched(body: ParentProductList?)

}
