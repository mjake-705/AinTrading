package com.ain.trading.utils

import com.ain.trading.customviews.MyApplication

object AinPref {

    fun getCustomerId() = SharedPreferenceHelper.getString(MyApplication.instance,  UserKey.USER_KEY ,"")

    fun setCustomerId(code: String) = SharedPreferenceHelper.putString(MyApplication.instance, UserKey.USER_KEY, code)

    fun getAuthToken() = SharedPreferenceHelper.getString(MyApplication.instance, UserKey.AUTH_TOKEN ,"")

    fun setAuthToken(code: String) = SharedPreferenceHelper.putString(MyApplication.instance, UserKey.AUTH_TOKEN, code)

    fun getPhone() = SharedPreferenceHelper.getString(MyApplication.instance, UserKey.PHONE ,"")

    fun setPhone(code: String) = SharedPreferenceHelper.putString(MyApplication.instance, UserKey.PHONE, code)
    fun getLanguageId() = SharedPreferenceHelper.getString(MyApplication.instance, UserKey.LANGUAGE_ID ,"0")

    fun setRegistrationId(reg_id: String) = SharedPreferenceHelper.putString(MyApplication.instance, UserKey.REGISTRATIONID, reg_id)
    fun getRegistrationId() = SharedPreferenceHelper.getString(MyApplication.instance, UserKey.REGISTRATIONID ,"0")

    fun setLanguageId(code: String) = SharedPreferenceHelper.putString(MyApplication.instance, UserKey.LANGUAGE_ID, code)


    fun getGuestUser() = SharedPreferenceHelper.getString(MyApplication.instance, UserKey.GUEST_ID ,"0")

    fun setGuestUser(code: String) = SharedPreferenceHelper.putString(MyApplication.instance, UserKey.GUEST_ID, code)

    fun getOnlanguageIsSet(): Boolean =
        SharedPreferenceHelper.getBoolean(MyApplication.instance, UserKey.ONLANGUAGE_SET, false)!!

    fun setOnlanguageIsSet(isSet: Boolean) =
        SharedPreferenceHelper.putBoolean(MyApplication.instance, UserKey.ONLANGUAGE_SET, isSet)

    fun getCartcount() = SharedPreferenceHelper.getString(MyApplication.instance, UserKey.CARTCOUNT ,"")

    fun setCartcount(cart_count: String) = SharedPreferenceHelper.putString(MyApplication.instance, UserKey.CARTCOUNT, cart_count)
}