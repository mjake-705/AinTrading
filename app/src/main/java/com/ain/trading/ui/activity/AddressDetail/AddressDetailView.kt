package com.ain.trading.ui.activity.AddressDetail

import com.ain.trading.api.DefaultAddressResponse
import com.ain.trading.api.GetCartlistResponse
import com.ain.trading.customviews.CommonView

interface AddressDetailView:CommonView {
    fun onCartFetched(body: GetCartlistResponse?)
    fun showdefaultAddressSuccesss(body: DefaultAddressResponse?)

}