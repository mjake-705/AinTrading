package com.ain.trading.ui.activity.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.ain.trading.R
import com.ain.trading.adapter.CategoryBrandTLAdapter
import com.google.android.material.tabs.TabLayout

class CategoriesDetailActivity : AppCompatActivity() {
    var from: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_detail)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        var categoryTabLayout: TabLayout? = null
        var categoryViewPager: ViewPager? = null
        if(intent.extras!=null&& intent.extras!!.containsKey("from")){
            from=intent?.getStringExtra("from")
        }

        categoryTabLayout = findViewById<TabLayout>(R.id.categoryTabLayout)
        categoryViewPager = findViewById<ViewPager>(R.id.categoryViewPager)

        categoryTabLayout?.addTab(categoryTabLayout!!.newTab().setText("CATEGORIES"))
        categoryTabLayout?.addTab(categoryTabLayout!!.newTab().setText("BRANDS"))

        categoryTabLayout?.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = CategoryBrandTLAdapter(this, supportFragmentManager, categoryTabLayout!!.tabCount)
        categoryViewPager?.adapter = adapter

        if(from?.equals("home")!!) {
            categoryViewPager?.setCurrentItem(1)
            adapter?.notifyDataSetChanged()
        }
        else {
            categoryViewPager?.setCurrentItem(0)
            adapter?.notifyDataSetChanged()
        }

        categoryViewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(categoryTabLayout))

        categoryTabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                categoryViewPager?.currentItem = tab.position
                adapter?.notifyDataSetChanged()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })




    }
}
