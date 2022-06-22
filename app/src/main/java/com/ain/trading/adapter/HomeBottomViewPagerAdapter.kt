package com.ain.trading.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HomeBottomViewPagerAdapter (fragmentManager: FragmentManager, mFragments:List<Fragment> ) : FragmentStatePagerAdapter(fragmentManager) {

    var mFragmentList = mFragments

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }


}