package com.ain.trading.api

import java.io.Serializable

data class Category(
    val categories: List<CategoryMain>,
    val statusCode: Int
)

data class CategoryMain(
    val category_id: String,
    val category_image: String,
    val children: List<Children>,
    val name: String
)

data class Children(
    val child_category_id: String,
    val child_category_image: String,
    val name: String
): Serializable


abstract class BaseItem {
    abstract fun getTitle(): String? }


data class RegistrationResponse(
    val StatusCode: Int,
    val additional: Any,
    val `data`: Datas,
    val message: String
)
data class Datas(
    val auth_token: String,
    val created_at: String,
    val device_id: Any,
    val dob: Any,
    val email: String,
    val email_verified: Int,
    val firstname: String,
    val gender: Any,
    val id: Int,
    val image: Any,
    val is_verified: Int,
    val language_id: Int,
    val last_login_at: Any,
    val last_login_ip: Any,
    val last_logout_at: Any,
    val lastname: String,
    val number: String,
    val number_verified: Int,
    val password: String,
    val push_token: Any,
    val remember_token: Any,
    val role_id: Int,
    val role_name: String,
    val role_token: String,
    val status_name: String,
    val updated_at: String,
    val user_type_id: Int,
    val username: Any
)
/*data class RegistrationResponse(
    val `data`: Datas,
    val message: Message
)*/

/*
data class Datas(
    val auth_token: String,
    val created_at: String,
    val device_id: Any,
    val device_token: Any,
    val email: String,
    val email_verified: Int,
    val message: Message,
    val firstname: String,
    val id: Int,
    val image: Any,
    val is_verified: Int,
    val language_id: Int,
    val last_login_at: Any,
    val last_login_ip: Any,
    val last_logout_at: Any,
    val lastname: String,
    val number: Any,
    val number_verified: Int,
    val password: String,
    val push_token: Any,
    val remember_token: Any,
    val role_id: Int,
    val role_name: String,
    val role_token: String,
    val status_name: String,
    val updated_at: String,
    val user_type_id: Int,
    val username: Any
)
*/

data class Message(
    val StatusCode: Int,
    val message: String
)
/*
data class Maincategory(
    val `data`: List<Data>,
    val message: Message,
    val pagination: String
)
data class Data(
    val category_id: Int,
    val category_slug: String,
    val category_title: String,
    val category_title_ar: Any,
    val category_token: String,
    val category_type: String,
    val created_at: String,
    val image: String,
    val image_path:String,
    val parent_id: String,
    val status_color: String,
    val status_color_code: String,
    val status_id: Int,
    val status_name: String,
    val status_slug: String,
    val status_token: String,
    val updated_at: String
)
*/

data class Maincategory(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<Data>,
    val message: String
)

data class Data(
    val category_id: Int,
    val category_slug: String,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val image: String,
    val image_path: String,
    val parent_id: Any
)

data class ParentCategory(
    val ParentSubCategories: List<ParentSubCategory>,
    val category_id: Int,
    val category_slug: String,
    val category_title: String,
    val category_title_ar: Any,
    val category_token: String,
    val category_type: String,
    val created_at: String,
    val image: Any,
    val parent_id: Int,
    val status_color: String,
    val status_id: Int,
    val status_name: String,
    val status_token: String,
    val updated_at: String
)
data class ParentSubCategory(
    val category_id: Int,
    val category_slug: String,
    val category_title: String,
    val category_title_ar: Any,
    val category_token: String,
    val category_type: String,
    val created_at: String,
    val image: Any,
    val parent_id: Int,
    val status_color: String,
    val status_id: Int,
    val status_name: String,
    val status_token: String,
    val updated_at: String
)

data class LoginResponse(
    val StatusCode: Int,
    val `data`: LoginData,
    val additional: Any,
    val message: String
)
data class LoginData(
    val auth_token: String,
    val created_at: String,
    val device_id: String,
    val dob: Any,
    val email: String,
    val email_verified: Int,
    val firstname: String,
    val gender: String,
    val id: Int,
    val image: Any,
    val is_verified: Int,
    val language_id: Int,
    val last_login_at: Any,
    val last_login_ip: Any,
    val last_logout_at: Any,
    val lastname: String,
    val number: Any,
    val number_verified: Int,
    val password: String,
    val push_token: Any,
    val remember_token: Any,
    val role_id: Int,
    val role_name: String,
    val role_token: String,
    val status_name: String,
    val updated_at: String,
    val user_type_id: Int,
    val username: Any
)

data class Banners(
    val StatusCode: Int,
    val additional: Any,
    val `data`: BannersData,
    val message: String
)
data class BannersData(
    val banner_id: Int,
    val banner_title: String,
    val images: List<Image>
)
data class Image(
    val banner_id: Int,
    val banner_image_id: Int,
    val image: String,
    val image_ext: String,
    val image_path: String,
    val image_size: Int
)


data class LatestProduct(
    val `data`: List<LatestData>,
    val message: Message,
    val pagination: String
)

data class LatestData(
    val created_at: String,
    val date_available: Any,
    val height: Any,
    val image_path: String,
    val length: Any,
    val location: Any,
    val manufacturer_id: Int,
    val manufacturer_name: String,
    val model: String,
    val price: String,
    val product_code: String,
    val product_id: Int,
    val product_image: String,
    val product_name: String,
    val product_token: String,
    val quantity: Int,
    val sku: Any,
    val status_color: String,
    val status_id: Int,
    val status_name: String,
    val status_token: String,
    val stock_status_id: Int,
    val stock_status_name: String,
    val unit_id: Int,
    val unit_name: String,
    val weight: Any,
    val width: Any
)




data class Country(
    val `data`: List<CountryState>,
    val message: Message,
    val pagination: String
)
data class CountryState(
    val country_id: Int,
    val country_name: String,
    val state_id: Int,
    val state_name: String
):BaseItem() {
    override fun getTitle(): String? = state_name

}

data class CountryCity(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<Cities>,
    val message: String
)

data class Cities(
    val city_id: Int,
    val city_name: String,
    val country_name: String,
    val state_id: Int,
    val state_name: String
):BaseItem() {
    override fun getTitle(): String? = city_name

}

/*data class CountryCity(
    val `data`: List<Cities>,
    val message: Message,
    val pagination: String
)
data class Cities(
    val city_id: Int,
    val city_name: String,
    val country_name: String,
    val state_id: Int,
    val state_name: String
):BaseItem() {
    override fun getTitle(): String? = city_name

}*/
/*
data class AddAddress(
    val `data`: AddressDetails,
    val message: Message,
    val pagination: String
)
data class AddressDetails(
    val address_holder: String,
    val address_type: String,
    val city_name: String,
    val country_name: String,
    val customer_to_address_id: Int,
    val default: Int,
    val land_marks: Any,
    val location: String,
    val postal_code: Int,
    val primary_address: String,
    val primary_number: String,
    val status_name: String

)
*/

data class AddAddress(
    val StatusCode: Int,
    val additional: Any,
    val `data`: List<AddressDetails>,
    val message: String
)

data class AddressDetails(
    val address_holder: String,
    val address_type: String,
    val city_name: String,
    val country_name: String,
    val customer_id: Int,
    val customer_to_address_id: Int,
    val default: Int,
    val land_marks: Any,
    val location: String,
    val postal_code: Int,
    val primary_address: String,
    val primary_number: String,
    val state_id: Int,
    val state_name: String,
    val status_name: String
)





data class ProductDetails(
    val StatusCode: Int,
    val additional: Any,
    val `data`: ProductDetailData,
    val message: String
)
data class ProductDetailData(
    val ProductToDescription: ProductToDescription,
    val images: List<Images>,
    val price: String,
    val product_code: String,
    val product_id: Int,
    val product_name: String,
    val product_token: String,
    val quantity: Int
)
data class ProductToDescription(
    val main_description: String,
    val product_to_description_id: Int,
    val short_description: String
)
data class Images(
    val image_path: String,
    val product_id: Int,
    val product_image: String,
    val product_to_image_id: Int
)




data class ProfileDetails(
    val StatusCode: Int,
    val additional: Any,
    val `data`: ProfileData,
    val message: String
)
data class ProfileData(
    val auth_token: String,
    val created_at: String,
    val device_id: String,
    val dob: Any,
    val email: String,
    val email_verified: Int,
    val firstname: String,
    val gender: String,
    val id: Int,
    val image: Any,
    val is_verified: Int,
    val language_id: Int,
    val last_login_at: Any,
    val last_login_ip: Any,
    val last_logout_at: Any,
    val lastname: String,
    val number: Any,
    val number_verified: Int,
    val password: String,
    val push_token: Any,
    val remember_token: Any,
    val role_id: Int,
    val role_name: String,
    val role_token: String,
    val status_name: String,
    val updated_at: String,
    val user_type_id: Int,
    val username: Any
)

/*data class WishListNew(
    val StatusCode: Int,
    val count: Int,
    val `data`: List<WishListData>,
    val message: String
)


data class WishListData(
    val customer_id: Int,
    val image_path: String,
    val price: String,
    val product_id: Int,
    val product_name: String,
    val wishlist_id: Int
)*/
data class WishListNew(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<WishListData>,
    val message: String
)
data class WishListData(
    val category: WishCategory,
    val customer_id: Int,
    val image_path: String,
    val price: String,
    val product_id: Int,
    val product_name: String,
    val wishlist_id: Int
)
data class WishCategory(
    val category_id: Int,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val parent_id: Int,
    val product_id: Int,
    val product_to_category_id: Int
)


/*data class StateResponse(
    val `data`: List<States>,
    val message: Message,
    val pagination: String
)

data class States(
    val country_id: Int,
    val country_name: String,
    val state_id: Int,
    val state_name: String
):BaseItem() {
        override fun getTitle(): String? = state_name

}*/

data class StateResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<States>,
    val message: String
)
data class States(
    val country_id: Int,
    val country_name: String,
    val state_id: Int,
    val state_name: String
):BaseItem() {
    override fun getTitle(): String? = state_name

}

data class RemoveWishlistNew(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<RemoveWishData>,
    val message: String
)

data class Additional(
    val pagination: String,
    val count: Int
)

data class RemoveWishData(
    val customer_id: Int,
    val image_path: String,
    val price: String,
    val product_id: Int,
    val product_name: String,
    val wishlist_id: Int
)

/*data class GetCartlistResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: CartData,
    val message: String
)
data class CartData(
    val cart_total: String,
    val cart_quantity: String,
    val grand_total: String,
    val items: List<Item>,
    val sub_total: String,
    val tax: String,
    val total_count: String,
    val total_discount: String
)

data class Item(
    val cart_id: Int,
    val cart_amount: String,
    val cart_quantity: Int,
    val customer_id: Int,
    val image_path: String,
    val price: String,
    val product_id: Int,
    val product_name: String,
    val unit_price: String
)*/

data class GetCartlistResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: CartData,
    val message: String
)
data class CartData(
    val cart_quantity: Int,
    val cart_total: String,
    val grand_total: String,
    val items: List<Item>,
    val sub_total: Int,
    val tax: String,
    val total_count: Int,
    val total_discount: String
)

data class Item(
    val cart_amount: String,
    val cart_id: Int,
    val cart_quantity: Int,
    val category: CartCategory,
    val customer_id: Int,
    val image_path: String,
    val price: String,
    val product_id: Int,
    val product_name: String,
    val quantity: Int

)
data class CartCategory(
    val category_id: Int,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val parent_id: Int,
    val product_id: Int,
    val product_to_category_id: Int
)

/*data class GetCartlistResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<CartData>,
    val message: String
)

data class CartData(
    val cart_id: Int,
    val cart_price: String,
    val cart_quantity: Int,
    val customer_id: Int,
    val image_path: String,
    val price: String,
    val product_id: Int,
    val product_name: String
)*/
data class CartAddedResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: CartAdded,
    val message: String
)

data class CartAdded(
    val cart_id: Int,
    val cart_price: String,
    val cart_quantity: Int,
    val customer_id: Int,
    val image_path: String,
    val price: String,
    val product_id: Int,
    val product_name: String
)




data class CartRemoveResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<CartDatas>,
    val message: String
)


data class CartDatas(
    val cart_id: Int,
    val cart_price: String,
    val cart_quantity: Int,
    val customer_id: Int,
    val image_path: String,
    val price: String,
    val product_id: Int,
    val product_name: String
)
/*data class PasswordChange(
    val `data`: ChangePassData,
    val message: Message,
    val pagination: String
)
data class ChangePassData(
    val auth_token: String,
    val created_at: String,
    val device_id: String,
    val dob: Any,
    val email: String,
    val email_verified: Int,
    val firstname: String,
    val gender: String,
    val id: Int,
    val image: Any,
    val is_verified: Int,
    val language_id: Int,
    val last_login_at: Any,
    val last_login_ip: Any,
    val last_logout_at: Any,
    val lastname: String,
    val number: Any,
    val number_verified: Int,
    val password: String,
    val push_token: Any,
    val remember_token: Any,
    val role_id: Int,
    val role_name: String,
    val role_token: String,
    val status_name: String,
    val updated_at: String,
    val user_type_id: Int,
    val username: Any
)*/

data class PasswordChange(
    val StatusCode: Int,
    val additional: Any,
    val `data`: ChangePassData,
    val message: String
)

data class ChangePassData(
    val auth_token: String,
    val created_at: String,
    val device_id: String,
    val dob: Any,
    val email: String,
    val email_verified: Int,
    val firstname: String,
    val gender: Any,
    val id: Int,
    val image: Any,
    val is_verified: Int,
    val language_id: Int,
    val last_login_at: Any,
    val last_login_ip: Any,
    val last_logout_at: Any,
    val lastname: String,
    val number: String,
    val number_verified: Int,
    val password: String,
    val push_token: Any,
    val remember_token: Any,
    val role_id: Int,
    val role_name: String,
    val role_token: String,
    val status_name: String,
    val updated_at: String,
    val user_type_id: Int,
    val username: Any
)

/*data class ListAddressResponse(
    val `data`: List<AddressList>,
    val message: Message,
    val pagination: String
)
data class AddressList(
    val address_holder: String,
    val address_type: String,
    val city_name: String,
    val country_name: String,
    val customer_to_address_id: Int,
    val default: Int,
    val land_marks: Any,
    val location: String,
    val postal_code: Int,
    val primary_address: String,
    val primary_number: String,
    val status_name: String
)*/



data class ListAddressResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<AddressList>,
    val message: String
)


data class AddressList(
    val address_holder: String,
    val address_type: String,
    val city_name: String,
    val country_name: String,
    val customer_id: Int,
    val customer_to_address_id: Int,
    val default: Int,
    val land_marks: Any,
    val location: String,
    val postal_code: Int,
    val primary_address: String,
    val primary_number: String,
    val state_id: Int,
    val state_name: String,
    val status_name: String
)


/*data class EditAddressResponse(
    val `data`: List<EditAddressLIst>,
    val message: Message,
    val pagination: String
)
data class EditAddressLIst(
    val address_holder: String,
    val address_type: String,
    val city_name: String,
    val country_name: String,
    val customer_to_address_id: Int,
    val default: Int,
    val land_marks: Any,
    val location: String,
    val postal_code: Int,
    val primary_address: String,
    val primary_number: String,
    val status_name: String
)*/

data class EditAddressResponse(
    val StatusCode: Int,
    val additional: Any,
    val `data`: List<EditAddressLIst>,
    val message: String
)

data class EditAddressLIst(
    val address_holder: String,
    val address_type: String,
    val city_name: String,
    val country_name: String,
    val customer_id: Int,
    val customer_to_address_id: Int,
    val default: Int,
    val land_marks: Any,
    val location: String,
    val postal_code: Int,
    val primary_address: String,
    val primary_number: String,
    val state_id: Int,
    val state_name: String,
    val status_name: String
)


/*data class FindAddressResponse(
    val `data`: FindAddress,
    val message: Message,
    val pagination: String
)
data class FindAddress(
    val address_holder: String,
    val address_type: String,
    val city_name: String,
    val state_name:String,
     val state_id:String,
    val country_name: String,
    val customer_id: Int,
    val customer_to_address_id: Int,
    val default: Int,
    val land_marks: Any,
    val location: String,
    val postal_code: Int,
    val primary_address: String,
    val primary_number: String,
    val status_name: String

)*/

data class FindAddressResponse(
    val StatusCode: Int,
    val additional: Any,
    val `data`: FindAddress,
    val message: String
)

data class FindAddress(
    val address_holder: String,
    val address_type: String,
    val address_type_id: String,
    val city_name: String,
    val country_name: String,
    val customer_id: Int,
    val customer_to_address_id: Int,
    val default: Int,
    val land_marks: Any,
    val location: String,
    val postal_code: Int,
    val primary_address: String,
    val primary_number: String,
    val state_id: Int,
    val city_id:Int,
    val state_name: String,
    val status_name: String
)

data class DeleteAddressResponse(
    val StatusCode: Int,
    val message: String
)
/*data class DefaultAddressResponse(
    val `data`: DefaultAddress,
    val message: Message,
    val pagination: String
)
data class DefaultAddress(
    val address_holder: String,
    val address_type: String,
    val city_name: String,
    val country_name: String,
    val customer_id: Int,
    val customer_to_address_id: Int,
    val default: Int,
    val land_marks: Any,
    val location: String,
    val postal_code: Int,
    val primary_address: String,
    val primary_number: String,
    val state_name: String,
    val status_name: String
)*/


data class DefaultAddressResponse(
    val StatusCode: Int,
    val additional: Any,
    val `data`: DefaultAddress,
    val message: String
)

data class DefaultAddress(
    val address_holder: String,
    val address_type_id: String,
    val address_type: String,
    val city_name: String,
    val country_name: String,
    val customer_id: Int,
    val customer_to_address_id: Int,
    val default: Int,
    val land_marks: Any,
    val location: String,
    val postal_code: Int,
    val primary_address: String,
    val primary_number: String,
    val state_id: Int,
    val state_name: String,
    val status_name: String
)
data class UpdatedCart(
    val StatusCode: Int,
    val `data`: UpdateCart,
    val additional: Any,
    val message: String
)

data class UpdateCart(
    val cart_id: Int,
    val cart_price: String,
    val cart_quantity: Int,
    val customer_id: Int,
    val image_path: String,
    val price: String,
    val product_id: Int,
    val product_name: String
)
data class BrandsList(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<BrandsData>,
    val message: String
)

data class BrandsData(
    val brand_id: Int,
    val brand_slug: String,
    val brand_title: String,
    val brand_token: String,
    val brand_type: String,
    val image: String,
    val image_path: String,
    val parent_id: Any
)
/*data class BrandsList(
    val `data`: List<BrandsData>,
    val message: Message,
    val pagination: String
)
data class BrandsData(
    val brand_id: Int,
    val brand_title: String,
    val brand_token: String,
    val brand_type: String,
    val image: String,
    val image_path: String,
    val parent_id: Any
)*/
/*data class ParentProductList(
    val `data`: List<Parentproduct>,
    val message: Message,
    val pagination: String
)

data class Parentproduct(
    val category: Categories,
    val image_path: String,
    val price: String,
    val product_code: String,
    val product_id: Int,
    val product_image: String,
    val product_name: String,
    val product_token: String
)
data class Categories(
    val category_id: Int,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val parent_id: Int,
    val product_id: Int,
    val product_to_category_id: Int
)*/



/*
data class ParentProductList(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<Parentproduct>,
    val message: String
)
data class Parentproduct(
    val category: Categories,
    val image_path: String,
    val price: String,
    val product_code: String,
    val product_id: Int,
    val product_name: String,
    val product_token: String,
    val quantity: Int
)
*/


data class ParentProductList(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<Parentproduct>,
    val message: String
)

data class Parentproduct(
    val category: Categories,
    val image_path: String,
    val price: String,
    val product_code: String,
    val product_id: Int,
    val product_name: String,
    val product_token: String,
    val quantity: Int
)


data class Categories(
    val category_id: Int,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val parent_id: Int,
    val product_id: Int,
    val product_to_category_id: Int
)


/*data class SubcategoryParentDetail(
    val `data`: List<SubparentCategoryData>,
    val message: Message,
    val pagination: String
)

data class SubparentCategoryData(
    val category_id: Int,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val image: Any,
    val image_path: String,
    val parent_id: Int
)*/
data class SubcategoryParentDetail(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<SubparentCategoryData>,
    val message: String
)

data class SubparentCategoryData(
    val category_id: Int,
    val category_slug: String,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val image: String,
    val image_path: String,
    val parent_id: Int
)
data class SubCategoryParent(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<SubCatParent>,
    val message: String
)
data class SubCatParent(
    val category_id: String,
    val category_slug: String,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val image: Any,
    val image_path: String,
    val parent_id: String
)

data class BrandDatailList(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<BrandDetailData>,
    val message: String
)
data class BrandDetailData(
    val category: CategorAll,
    val image_path: String,
    val price: String,
    val product_code: String,
    val product_id: Int,
    val product_name: String,
    val product_token: String,
    val quantity: Int
)
data class CategorAll(
    val category_id: Int,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val parent_id: Any,
    val product_id: Int,
    val product_to_category_id: Int
)
/*data class RelatedProducts(
    val `data`: List<RelatesProducts>,
    val message: Message,
    val pagination: String
)

data class RelatesProducts(
    val category: Categorie,
    val image_path: String,
    val price: String,
    val product_code: String,
    val product_id: Int,
    val product_image: String,
    val product_name: String,
    val product_token: String
)

data class Categorie(
    val category_id: Int,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val parent_id: Any,
    val product_id: Int,
    val product_to_category_id: Int
)*/

data class RelatedProducts(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<RelatesProducts>,
    val message: String
)

data class RelatesProducts(
    val category: Categoriees,
    val image_path: String,
    val price: String,
    val product_code: String,
    val product_id: Int,
    val product_name: String,
    val product_token: String,
    val quantity: Int
)
data class Categoriees(
    val category_id: Int,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val parent_id: Any,
    val product_id: Int,
    val product_to_category_id: Int
)

/*data class SearchResponse(
    val `data`: List<SearchData>,
    val message: Message,
    val pagination: String
)

data class SearchData(
    val category: SearchCategory,
    val image_path: String,
    val price: String,
    val product_code: String,
    val product_id: Int,
    val product_image: String,
    val product_name: String,
    val product_token: String
)
data class SearchCategory(
    val category_id: Int,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val parent_id: Int,
    val product_id: Int,
    val product_to_category_id: Int
)*/

data class SearchResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<SearchData>,
    val message: String
)
data class SearchData(
    val category: SearchCategory,
    val image_path: String,
    val price: String,
    val product_code: String,
    val product_id: Int,
    val product_name: String,
    val product_token: String,
    val quantity: Int
)
data class SearchCategory(
    val category_id: Int,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val parent_id: Any,
    val product_id: Int,
    val product_to_category_id: Int
)


data class QuantityResponse(
    val StatusCode: Int,
    val additional: Any,
    val `data`: Int,
    val message: String
)



data class AddWishList(
    val StatusCode: Int,
    val additional: Any,
    val `data`: WishlistAdd,
    val message: String
)

data class WishlistAdd(
    val customer_id: Int,
    val image_path: String,
    val price: String,
    val product_id: Int,
    val product_name: String,
    val wishlist_id: Int
)
data class OTPResponse(
    val StatusCode: Int,
    val additional: Any,
    val `data`: OtpData,
    val message: String
)

data class OtpData(
    val auth_token: String,
    val created_at: String,
    val device_id: Any,
    val dob: Any,
    val email: String,
    val email_verified: Int,
    val firstname: String,
    val gender: Any,
    val id: Int,
    val image: Any,
    val is_verified: Int,
    val language_id: Int,
    val last_login_at: Any,
    val last_login_ip: Any,
    val last_logout_at: Any,
    val lastname: String,
    val number: String,
    val number_verified: Int,
    val password: String,
    val push_token: Any,
    val remember_token: Any,
    val role_id: Int,
    val role_name: String,
    val role_token: String,
    val status_name: String,
    val updated_at: String,
    val user_type_id: Int,
    val username: Any
)

data class PaymentResponse(
    val StatusCode: Int,
    val additional: Any,
    val `data`: String,
    val message: String
)

data class Orders(
    val StatusCode: Int,
    val `data`: List<MyOrders>,
    val additional: Additional,
    val message: String
)

data class MyOrders(
    val created_at: String,
    val email: String,
    val firstname: String,
    val grand_total: String,
    val invoice_prefix: String,
    val order_id: Int,
    val payment_mode_id: Int,
    val payment_type: String,
    val status_type: String
)
data class OrderlistResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<Orderlist>,
    val message: String
)

data class Orderlist(
    val image_path: String,
    val order_id: Int,
    val order_product_id: Int,
    val price: String,
    val product_id: Int,
    val product_name: String,
    val quantity: Int,
    val total: String,
    val unit_price: String
)

data class SearchPostalResponse(
    val StatusCode: Int,
    val additional: Any,
    val `data`: SearchPostalData,
    val message: String
)

data class SearchPostalData(
    val city_id: Int,
    val city_name: String,
    val city_slug: String,
    val postal_code: Int,
    val postal_code_id: Int
)

data class LanguageUpdate(
    val StatusCode: Int,
    val `data`:LanguageData,
    val additional: Any,
    val message: String
)
data class LanguageData(
    val auth_token: String,
    val created_at: String,
    val device_id: String,
    val dob: Any,
    val email: String,
    val email_verified: Int,
    val firstname: String,
    val gender: String,
    val id: Int,
    val image: Any,
    val is_verified: Int,
    val language_id: String,
    val last_login_at: Any,
    val last_login_ip: Any,
    val last_logout_at: Any,
    val lastname: String,
    val number: String,
    val number_verified: Int,
    val password: String,
    val push_token: Any,
    val remember_token: Any,
    val role_id: Int,
    val role_name: String,
    val role_token: String,
    val status_name: String,
    val updated_at: String,
    val user_type_id: Int,
    val username: Any
)



/*data class FilterResponse(
    val StatusCode: Int,
    val `data`: List<FilterData>,
    val additional: Additional,
    val message: String
)
data class FilterData(
    val filter_id: Int,
    val filter_slug: String,
    val filter_title: String,
    val filter_token: String,
    val filter_type: String,
    val parent_id: Any,
    var isSelected:Boolean

)*/


data class FilterResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<FilterData>,
    val message: String
)
data class FilterData(
    val childs: List<Child>,
    val filter_id: Int,
    val filter_slug: String,
    val filter_title: String,
    val filter_token: String,
    val filter_type: String,
    val parent_id: Any
)
data class Child(
    val filter_id: Int,
    val filter_slug: String,
    val filter_title: String,
    val filter_token: Any,
    val filter_type: String,
    val parent_id: Int,
    var isSelected:Boolean
)


/*

data class FilterItems(
    val StatusCode: Int,
    val `data`: List<FilterItemsData>,
    val additional: Additional,
    val message: String
)

data class FilterItemsData(
    val filter_id: Int,
    val filter_slug: String,
    val filter_title: String,
    val filter_token: Any,
    val filter_type: String,
    val parent_id: Int,
    val isChecked:Boolean
):BaseItem() {
    override fun getTitle(): String? = filter_title
}
*/





data class PaymentTypeResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<PaymentTypeResponseData>,
    val message: String
)

data class PaymentTypeResponseData(
    val created_at: String,
    val payment_mode_id: Int,
    val payment_type: String,
    val status_color: String,
    val status_color_code: String,
    val status_id: Int,
    val status_name: String,
    val status_slug: String,
    val status_token: String,
    val updated_at: String
)

data class FilterProductsResponse(
    val StatusCode: Int,
    val additional: Additional,
    val `data`: List<FilterProductsData>,
    val message: String
)
data class FilterProductsData(
    val category: FilterProductsCategory,
    val image_path: String,
    val price: String,
    val product_code: String,
    val product_id: Int,
    val product_name: String,
    val product_token: String,
    val quantity: Int
)
data class FilterProductsCategory(
    val category_id: Int,
    val category_title: String,
    val category_token: String,
    val category_type: String,
    val parent_id: Int,
    val product_id: Int,
    val product_to_category_id: Int
)



