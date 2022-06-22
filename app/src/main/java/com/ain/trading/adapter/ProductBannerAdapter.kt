package com.ain.trading.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.ain.trading.R
import com.ain.trading.api.Image
import com.ain.trading.api.Images
import com.ain.trading.customviews.MyApplication
import com.squareup.picasso.Picasso


class ProductBannerAdapter(val mBannerList:MutableList<Images>) : PagerAdapter() {
    val Image = arrayOf(R.drawable.banner , R.drawable.banner , R.drawable.banner)



    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return  mBannerList.size
        //return  3
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val myImageLayout = LayoutInflater.from(MyApplication.instance).inflate(R.layout.detail_banner_vp_items, view, false)
        val mBannerIV = myImageLayout.findViewById(R.id.detail_bannerIV) as ImageView
        //mBannerIV.loadUrl(mBannerList[position].image!!)
        if(!mBannerList[position].image_path.isNullOrEmpty()) {

            Picasso.with(MyApplication.instance).load(mBannerList[position]?.image_path).fit()
                .placeholder(R.drawable.bg_dummy)
                .error(R.drawable.bg_dummy)
                .into(mBannerIV)
        }

        view.addView(myImageLayout, 0)
        return myImageLayout
    }
}