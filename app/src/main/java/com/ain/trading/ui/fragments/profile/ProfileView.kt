package com.ain.trading.ui.fragments.profile

import com.ain.trading.api.LanguageUpdate
import com.ain.trading.api.ProfileDetails
import com.ain.trading.customviews.CommonView

interface ProfileView : CommonView {
    fun OnProfileDetailFetched(body: ProfileDetails?)
     fun OnProfileUpdateSuccess(body: ProfileDetails?)
    fun OnProfileLanguageUpdateSuccess(body: LanguageUpdate?)


}