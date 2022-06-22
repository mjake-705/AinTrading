package com.ain.trading.ui.activity.MyAddress

import com.ain.trading.api.DeleteAddressResponse
import com.ain.trading.api.ListAddressResponse
import com.ain.trading.customviews.CommonView

interface MyAddressView:CommonView {
    fun onAddressListFetched(body: ListAddressResponse?)
    fun onDeletedAddressSuccessfully(body: DeleteAddressResponse?)



}