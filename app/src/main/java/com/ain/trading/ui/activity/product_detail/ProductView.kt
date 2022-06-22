package com.ain.trading.ui.activity.product_detail

import com.ain.trading.api.*
import com.ain.trading.customviews.CommonView
import retrofit2.Call

interface ProductView : CommonView {
    fun OnProductDetailFetched(body: ProductDetails?)
    fun OnAddWishListSuccess(body: AddWishList?)
    fun OnAddCartListSuccess(body: CartAddedResponse)
    fun OnRelatedProductListSuccess(body: RelatedProducts)
    fun OnSearchPostalCodeSuccess(body: SearchPostalResponse)

}