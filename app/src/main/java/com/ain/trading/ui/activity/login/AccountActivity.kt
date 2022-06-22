package com.ain.trading.ui.activity.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.ain.trading.R
import com.ain.trading.adapter.MyAdapter
import com.ain.trading.utils.AinPref
import com.ain.trading.utils.ManagePermissions
import com.google.android.material.tabs.TabLayout
import android.hardware.usb.UsbDevice.getDeviceId
import androidx.annotation.RequiresApi
import android.provider.Settings


class AccountActivity : AppCompatActivity() {
    var telephonyManager: TelephonyManager?=null
    private val PermissionsRequestCode = 123
    private var managePermissions: ManagePermissions? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_account)
        var tabLayout: TabLayout? = null
        var viewPager: ViewPager? = null
        val deviceID = Settings.Secure.getString(contentResolver,
            Settings.Secure.ANDROID_ID)
        AinPref.setCustomerId(deviceID)

        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("SignIn"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("SignUp"))

        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MyAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }


}


