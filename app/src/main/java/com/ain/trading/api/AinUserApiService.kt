package com.ain.trading.api

import retrofit2.Call
import retrofit2.http.*

interface AinUserApiService {

    @FormUrlEncoded
    @POST("customer-registration")
    fun registration(@Field("firstname") firstname: String,
                     @Field("lastname") product_id: String,
                     @Field("number") number: String,
                     @Field("email") customer_id: String,
                     @Field("mainpassword") mainpassword: String,
                     @Field("confirm_password") confirm_password: String,
                     @Field("user_type_id") user_type_id: String,
                     @Field("language") language: String
    ): Call<RegistrationResponse>



    @GET("categories")
    fun getCategoryList(): Call<Maincategory>

    @GET("banners")
    fun getBanners(@Query("banner_id") customer_id: String
    ): Call<Banners>


    @GET("banners")
    fun getbrandinFocus(@Query("banner_id") customer_id: String
    ): Call<Banners>


    @GET("products")
    fun getLatestProduct(@Query("keyword") keyword: String
    ): Call<ParentProductList>

    @GET("products")
    fun getProductDetail(@Query("product_id") product_id: String
    ): Call<ProductDetails>



    @GET("customer-details")
    fun getProfileDetails(): Call<ProfileDetails>

    @FormUrlEncoded
    @POST("profile-update")
    fun  updateProfileDetails(@Field("firstname") customer_id: String,
                              @Field("lastname") mainpassword: String,
                              @Field("gender") confirm_password: String
    ): Call<ProfileDetails>


    @FormUrlEncoded
    @POST("language-update")
    fun  updateProfilLanguage(@Field("language") language: String
    ): Call<LanguageUpdate>

    @GET("get-wishlist")
    fun  getWishList(): Call<WishListNew>

    @FormUrlEncoded
    @POST("add-wishlist")
    fun  addWishList(@Field("keyword") keyword: String, @Field("product_id") product_id: String): Call<AddWishList>


    @FormUrlEncoded
    @POST("customer-login")
    fun login(@Field("email") customer_id: String,
              @Field("password") mainpassword: String,
              @Field("device_id") confirm_password: String
    ): Call<LoginResponse>




    @FormUrlEncoded
    @POST("states")
    fun getRegion(@Field("country_id") country_id: String?): Call<StateResponse>

    @FormUrlEncoded
    @POST("cities")
    fun getSubRegion(@Field("state_id") state_id: String?): Call<CountryCity>

    @FormUrlEncoded
    @POST("customer-address")
    fun addressAdding(@Field("keyword") keyword: String,
        @Field("address_holder") address_holder: String,
                      @Field("primary_number") primary_number: String,
                      @Field("primary_address") primary_address: String,
                      @Field("address_type_id") address_type_id: String,
                      @Field("location") location: String,
                      @Field("postal_code") postal_code: String,
                      @Field("city_id") city_id: String,
                      @Field("default") default: String


    ): Call<AddAddress>

    @FormUrlEncoded
    @POST("customer-address")
    fun findparticularAddress(@Field("keyword") keyword: String,
                              @Field("customer_to_address_id") customer_to_address_id: String?



    ): Call<FindAddressResponse>

    @POST("customer-address")
    fun getAddressList(): Call<ListAddressResponse>


    @FormUrlEncoded
    @POST("customer-address")
    fun editaddresslist(@Field("keyword") keyword: String,
                       @Field("address_holder") address_holder: String,
                      @Field("primary_number") primary_number: String,
                      @Field("primary_address") primary_address: String,
                      @Field("address_type_id") address_type_id: String,
                      @Field("location") location: String,
                      @Field("postal_code") postal_code: String,
                        @Field("city_id") city_id: String,
                      @Field("default") default: String,
                        @Field("customer_to_address_id") customer_to_address_id: String
    ): Call<EditAddressResponse>

    @FormUrlEncoded
    @POST("customer-address")
    fun setdefaultAddress(@Field("keyword") keyword: String,
                          @Field("primary_number") primary_number: String,
                        @Field("default") default: String,
                        @Field("customer_to_address_id") customer_to_address_id: String
    ): Call<EditAddressResponse>

    @FormUrlEncoded
    @POST("customer-address")
    fun deleteAddress(@Field("keyword") keyword: String,

                          @Field("customer_to_address_id") customer_to_address_id: String
    ): Call<DeleteAddressResponse>

    @FormUrlEncoded
    @POST("customer-address")
    fun defaultAddress(@Field("keyword") keyword: String
                          ): Call<DefaultAddressResponse>

    @FormUrlEncoded
    @POST("remove-wishlist")
    fun  removeWishList( @Field("wishlist_id") wishlist_id: String): Call<RemoveWishlistNew>

    @FormUrlEncoded
    @POST("add-cart")
    fun  addCartList(@Field("product_id") product_id: String, @Field("cart_quantity") cart_quantity: String): Call<CartAddedResponse>
    @FormUrlEncoded
    @POST("remove-cart")
    fun removeCart(@Field("cart_id") cart_id: String
    ): Call<CartRemoveResponse>
    @FormUrlEncoded
    @POST("states")
    fun getRegion(@Field("country_id") country_id: Int?): Call<StateResponse>

    @GET("get-cart")
    fun getCart(): Call<GetCartlistResponse>
    @FormUrlEncoded
    @POST("password-update")
    fun changePassword(@Field("old_password") old_password: String?,@Field("new_password")password:String,@Field("confirm_password")confirm_password:String): Call<PasswordChange>


    @FormUrlEncoded
    @POST("forgot-password")
    fun forgotPassword(@Field("email") email: String?): Call<DeleteAddressResponse>
    @FormUrlEncoded
    @POST("update-cart")
    fun updateCart(@Field("cart_id") product_id: String,@Field("cart_quantity") cart_quantity: String
    ): Call<UpdatedCart>

    @GET("categories")
    fun getCategoryDetails(@Query("category_id") category_id: String
    ): Call<SubCategoryParent>

    @GET("categories")
    fun getSubCategoryDetails(@Query("category_id") category_id: String
    ): Call<SubcategoryParentDetail>
    @GET("brands")
    fun getBrands(): Call<BrandsList>
   /* @GET("products")
    fun getParentProduct(@Query("category_token") category_token: String
    ): Call<ParentProductList>*/
    @GET("category-products")
    fun getParentProduct(@Query("category_id") category_id: String
    ): Call<ParentProductList>

    @GET("brand-products")
    fun getBrandsDetailsList(@Query("brand_id") brand_id: String
    ): Call<BrandDatailList>
    @GET("banners")
    fun getCategoryBanner(@Query("banner_id") customer_id: String
    ): Call<Banners>
    @GET("related-products")
    fun getRelatedProducts(@Query("category_id") category_id: String?, @Query("product") product: String?): Call<RelatedProducts>

    @GET("search-products")
    fun getSearchData(@Query("searchQuery") keyword: String
    ): Call<SearchResponse>
    @GET("purchase-count")
    fun getQuantityData(@Query("product_id")product_id:String
    ): Call<QuantityResponse>


    @FormUrlEncoded
    @POST("otp-verification")
    fun getOTP(@Field("number") number: String?,@Field("otp")otp:String,@Field("id")id:String): Call<OTPResponse>


    @FormUrlEncoded
    @POST("check-out")
    fun onPayment(@Field("customer_to_address_id") customer_to_address_id: String?,@Field("payment_mode_id")payment_mode_id:String,@Field("shipping_rate")shipping_rate:String): Call<PaymentResponse>


    @GET("customer-orders")
    fun getOrders(): Call<Orders>


    @FormUrlEncoded
    @POST("order-products")
    fun getOrderedProducts(@Field("order_id") order_id: String?): Call<OrderlistResponse>

    @GET("search-postal-codes")
    fun searchpostal(@Query("postal_code")postal_code:String
    ): Call<SearchPostalResponse>

    @GET("filters")
    fun getFilter(): Call<FilterResponse>

  /*  @GET("parent-filters")
    fun getFilterItems(@Query("filter_id")filter_id:String): Call<FilterItems>*/

    @GET("payment-modes")
    fun getpaymentType(): Call<PaymentTypeResponse>


    @FormUrlEncoded
    @POST("filter-products")
    fun getFilterProducts(@Field("filter_id") filter_id: String?): Call<ParentProductList>


}


