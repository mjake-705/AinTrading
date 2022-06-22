package com.ain.trading.ui.activity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ain.trading.R
import com.ain.trading.adapter.HomeBottomViewPagerAdapter
import com.ain.trading.customviews.AINCustomViewpager
import com.ain.trading.ui.activity.login.AccountActivity
import com.ain.trading.ui.fragments.Fragment_cart
import com.ain.trading.ui.fragments.wishlist.FragmentWishList
import com.ain.trading.ui.fragments.Home_fragment
import com.ain.trading.ui.fragments.fragment_Category
import com.ain.trading.ui.fragments.profile.FragmentProfile
import com.ain.trading.utils.AinAlertDialog
import com.ain.trading.utils.AinPref
import com.ain.trading.utils.AinSignInAlertDialog
import com.ain.trading.utils.SharedPreferenceHelper
import kotlinx.android.synthetic.main.home_bottom_layout.*

class HomeActivity : AppCompatActivity(),Fragment_cart.Listener {

    override fun oncartUpdate() {


if(AinPref.getCartcount().toString()!="null"){
    cartCountLL.visibility=View.VISIBLE
    cart_count.visibility=View.VISIBLE
    cart_count.text=AinPref.getCartcount()
}
        else{
    cart_count.visibility=View.GONE
    cartCountLL.visibility=View.GONE
        }
    }

    var logOutDialog: AinSignInAlertDialog?=null
    var isClickDisacle: Boolean = true
    var homePageAdapter: HomeBottomViewPagerAdapter? = null
    var from: String?=null
    var homeVP: AINCustomViewpager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initViewPager()

        handleViewPagerActions()

        if(intent.extras!=null&& intent.extras!!.containsKey("from")){
            from=intent.getStringExtra("from")
            if(from.equals("product"))
            {
                homeVP?.currentItem = 2
                wishlistshow()
            }

         else   if(from.equals("cart"))
            {
                homeVP?.currentItem = 3
                cartListShow()
            }

        }

    }

    private fun handleViewPagerActions() {
        homeMallCL.setOnClickListener {
            if (isClickDisacle) {
                homeVP?.currentItem = 0
     /* homeScanTV.setTextColor(ContextCompat.getColor(this, R.color.tab_unselected))
      earnBurnTV.setTextColor(ContextCompat.getColor(this, R.color.tab_unselected))
       homeServiceTV.setTextColor(ContextCompat.getColor(this, R.color.tab_unselected))
       homeMoreTV.setTextColor(ContextCompat.getColor(this, R.color.tab_unselected))
      mallTV.setTextColor(ContextCompat.getColor(this, R.color.color_bt))*/
                mallTV.setTextColor(ContextCompat.getColor(this, R.color.colorPink))
                earnBurnTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                homeScanTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                homeServiceTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                homeMoreTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
              //  homeGiftIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cat_selected))
                homeScanIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.wishlist_icon))
                homeServiceIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cart_icon))
                homeMoreIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profile_icon))
                homeGiftIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.category_icon))
            }
        }
        homeScanCL.setOnClickListener {

            if(AinPref.getGuestUser().equals("3"))
            {
                onNotLogined()
            }
            else if(AinPref.getGuestUser().equals("0")){
                homeVP?.currentItem=2
               wishlistshow()
            }

        }

        cart_Cl.setOnClickListener {

            if(AinPref.getGuestUser().equals("3"))
            {
                onNotLogined()
            }
            else if(AinPref.getGuestUser().equals("0")){
                homeVP?.currentItem=3
               cartListShow()
            }

        }
        homeMoreCL.setOnClickListener {
            if(AinPref.getGuestUser().equals("3"))
            {
                onNotLogined()
            }
            else if(AinPref.getGuestUser().equals("0")){
                homeVP?.currentItem=4
                mallTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                earnBurnTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                homeScanTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                homeServiceTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                homeMoreTV.setTextColor(ContextCompat.getColor(this, R.color.colorPink))
                homeMoreIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profile_select))
                homeScanIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.wishlist_icon))
                homeServiceIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cart_icon))

                homeGiftIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.category_icon))
            }

        }
        category_Cl.setOnClickListener {
            homeVP?.currentItem=1
            mallTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
            earnBurnTV.setTextColor(ContextCompat.getColor(this, R.color.colorPink))
            homeScanTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
            homeServiceTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
            homeMoreTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
            homeGiftIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.category_pink))
            homeScanIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.wishlist_icon))
            homeServiceIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cart_icon))
            homeMoreIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profile_icon))

        }
    }
    private fun onNotLogined() {
        logOutDialog = AinSignInAlertDialog(this!!)

        logOutDialog!!.show(getString(R.string.signin),
            this!!.getDrawable(R.drawable.login_not)!!,getString(R.string.please), getString(R.string.yes),getString(R.string.no),
            positiveButtonListener = View.OnClickListener {
                logOutDialog!!.dismiss()
                val intent = Intent(this, AccountActivity::class.java)
                AinPref.setGuestUser("0")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            },negativeButtonListener = View.OnClickListener {
                logOutDialog!!.dismiss()
            })
    }
    private fun cartListShow() {
        mallTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        earnBurnTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        homeScanTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        homeServiceTV.setTextColor(ContextCompat.getColor(this, R.color.colorPink))
        homeMoreTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        homeServiceIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cart_pink))
        homeScanIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.wishlist_icon))

        homeMoreIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profile_icon))
        homeGiftIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.category_icon))
    }

    private fun wishlistshow() {
        mallTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        earnBurnTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        homeScanTV.setTextColor(ContextCompat.getColor(this, R.color.colorPink))
        homeServiceTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        homeMoreTV.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        homeScanIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.wishlist_pink))
        homeMoreIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profile_icon))

        homeServiceIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cart_icon))

        homeGiftIV.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.category_icon))
    }


    private fun initViewPager() {
        val mFragmentList = ArrayList<Fragment>()
        mFragmentList.add(Home_fragment())
        mFragmentList.add(fragment_Category())
        mFragmentList.add(FragmentWishList())
        mFragmentList.add(Fragment_cart())
        mFragmentList.add(FragmentProfile())


        homePageAdapter = HomeBottomViewPagerAdapter(supportFragmentManager, mFragmentList)
        homeVP = findViewById(R.id.homeViewPager)
        homeVP?.adapter = homePageAdapter
        homeVP?.offscreenPageLimit = 4
        homeVP?.currentItem = 0
        homeVP?.setEnableSwipe(false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == 100) {

            val requiredValue = data?.getIntExtra("key",0)
            if(requiredValue==2) {
                homeVP?.currentItem = requiredValue
            wishlistshow()
            }

            if(requiredValue==3) {
                homeVP?.currentItem = requiredValue
                cartListShow()
            }

        }

    }





}