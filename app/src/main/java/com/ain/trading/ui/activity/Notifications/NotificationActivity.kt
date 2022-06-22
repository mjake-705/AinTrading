package com.ain.trading.ui.activity.Notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.NotificationAdapter
import com.ain.trading.api.Children
import com.ain.trading.customviews.ServiceObject
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.app_bar_center_title.*

class NotificationActivity : AppCompatActivity() {
    var mNotificationAdapter: NotificationAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initToolBar()
        inititemslist()
    }
    private fun inititemslist() {

        val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.shoesimage))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.women))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.shoesimage))
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.shoesimage))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.women))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.shoesimage))
        mNotificationAdapter=NotificationAdapter (value,object : NotificationAdapter.OnchildCallBack{
            override fun onChildClick(position: Int, mChildrenList: MutableList<Children>) {

            }

            override fun onServiceClickCallBack(position: Int) {
            }


        })









        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        notificationRV .layoutManager = horizontalLayoutManager
        notificationRV.adapter = mNotificationAdapter


    }


    private fun initToolBar() {
        setSupportActionBar(notification_listToolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text =  getString(R.string.notification)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}
