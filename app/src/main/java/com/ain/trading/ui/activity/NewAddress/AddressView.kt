package com.ain.trading.ui.activity.NewAddress

import com.ain.trading.api.*
import com.ain.trading.customviews.CommonView

interface AddressView: CommonView {
    fun onCountryRegionFetched(body: StateResponse?)
    fun onCountryCityFetched(body: CountryCity?)

   fun onAddressAddingSuccesss(body: AddAddress?)
}