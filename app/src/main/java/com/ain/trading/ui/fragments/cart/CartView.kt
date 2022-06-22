package com.ain.trading.ui.fragments.cart

import com.ain.trading.api.AddWishList
import com.ain.trading.api.CartRemoveResponse


import com.ain.trading.api.GetCartlistResponse
import com.ain.trading.api.UpdatedCart
import com.ain.trading.customviews.CommonView

interface CartView:CommonView {
     fun onCartFetched(body: GetCartlistResponse?)
    fun onCartRemovedSuccesfully(body: CartRemoveResponse?)
    fun onCartUpdatedSuccesfully(body: UpdatedCart?)
    fun onQuantityFetched(data: List<String>)
   fun OnAddWishListSuccess(body: AddWishList?)


}