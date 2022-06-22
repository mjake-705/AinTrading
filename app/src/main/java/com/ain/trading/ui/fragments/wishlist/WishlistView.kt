package com.ain.trading.ui.fragments.wishlist

import com.ain.trading.api.RemoveWishlistNew
import com.ain.trading.api.WishListNew
import com.ain.trading.customviews.CommonView

interface WishlistView:CommonView{
     fun OnWishListFetched(body: WishListNew?)
     fun onWishlistRemoveSuccess(body: RemoveWishlistNew?)
}