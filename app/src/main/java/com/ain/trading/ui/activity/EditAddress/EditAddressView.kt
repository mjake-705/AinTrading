package com.ain.trading.ui.activity.EditAddress

import com.ain.trading.api.EditAddressResponse
import com.ain.trading.api.FindAddressResponse
import com.ain.trading.customviews.CommonView

interface EditAddressView:CommonView {
    fun onEditAddressSuccesss(body: EditAddressResponse?)
    fun setDefaulttAddressSuccesss(body: EditAddressResponse?)
    fun setFindAddressSuccesss(body: FindAddressResponse?)



}