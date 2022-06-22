package com.ain.trading.ui.activity.home

import com.ain.trading.api.*
import com.ain.trading.customviews.CommonView

interface HomeView:CommonView{
    fun onCategoryFetched(body: Maincategory?)
    fun onBannersFetched(body: Banners?)
    fun onBrandinFocusfetched(body: Banners?)
    fun onLatestProductfetched(body: ParentProductList?)
    fun OnAddCartListSuccess(body: CartAddedResponse)
    fun onCategoryDetailFetched(body: SubCategoryParent?)
    fun onSubCategoryDetailFetched(body: SubcategoryParentDetail?)
    fun onSubCategoryProductsFetched(body: ParentProductList?)
    fun onBrandsFetched(body: BrandsList?)
    fun onBrandsListFetched(body: BrandDatailList?)
    fun onCategoryBannerFetched(body: Banners?)
}