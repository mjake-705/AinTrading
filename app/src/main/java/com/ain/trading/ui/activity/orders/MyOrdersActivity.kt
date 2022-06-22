package com.ain.trading.ui.activity.orders

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.MyordersAdapter
import com.ain.trading.api.Children
import com.ain.trading.api.MyOrders
import com.ain.trading.api.OrderlistResponse
import com.ain.trading.api.Orders
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.orderlisting.OrderListingActivity
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.checkNetworkConnectivity
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_my_orders.*
import kotlinx.android.synthetic.main.app_bar_center_title.*

class MyOrdersActivity : AppCompatActivity(),OrdersView {
    override fun onOrdersFetchedItemFetched(body: OrderlistResponse?) {
    }

    override fun onOrdersFetched(body: Orders?) {
        orderlist?.clear()
        if (body?.StatusCode==6000) {
            orderlist?.addAll(body?.data)
            mMyordersAdapter?.notifyDataSetChanged()
            myOdersRV.visibility=View.VISIBLE
            no_orders_Tv.visibility=View.GONE
        }
        else if(body?.StatusCode==6001)
        {
            myOdersRV.visibility=View.GONE
            no_orders_Tv.visibility=View.VISIBLE
        }

    }

    override fun onError(error: String?) {
     this.showSnackBar(error.toString())
    }

    override fun showProgress() {
loadingDialog.show()
    }

    override fun hideProgress() {
        loadingDialog.cancel()

    }
    private var orderlist:MutableList<MyOrders> = ArrayList<MyOrders>()
    private lateinit var ordersPresenter: OrdersPresenter
    private lateinit var loadingDialog: LoadingDialog
    var mMyordersAdapter: MyordersAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        loadingDialog= LoadingDialog(this)
        ordersPresenter=OrdersPresenter(this)
        if (this?.checkNetworkConnectivity()!!) {
            ordersPresenter.getOrders()
        }
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
        mMyordersAdapter=MyordersAdapter (orderlist,object : MyordersAdapter.OnchildCallBack{
            override fun onChildClick(position: Int, mChildrenList: MutableList<Children>) {

            }

            override fun onServiceClickCallBack(position: Int, order_id: String) {
                startActivity(Intent(this@MyOrdersActivity, OrderListingActivity::class.java).putExtra("order_id", order_id))
            }


        })









        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myOdersRV .layoutManager = horizontalLayoutManager
        myOdersRV.adapter = mMyordersAdapter


    }
    private fun initToolBar() {
        setSupportActionBar(ordersToolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text =  getString(R.string.orderz)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
