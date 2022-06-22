package com.ain.trading.ui.activity.orderlisting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.Order_listingAdapter
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.orders.OrdersPresenter
import com.ain.trading.ui.activity.orders.OrdersView
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_order_listing.*
import kotlinx.android.synthetic.main.app_bar_center_title.*

class OrderListingActivity : AppCompatActivity(),OrdersView {
    override fun onOrdersFetchedItemFetched(body: OrderlistResponse?) {
        orderitemlist?.clear()
        if (body?.StatusCode==6000) {
            orderitemlist?.addAll(body?.data)
            mMyordersAdapter?.notifyDataSetChanged()
            no_items_TV.visibility= View.GONE
            myOders_list_RV.visibility= View.VISIBLE
        }
        else if(body?.StatusCode==6001)
        {
            no_items_TV.visibility= View.VISIBLE
            myOders_list_RV.visibility=View.GONE
        }
    }

    override fun onOrdersFetched(body: Orders?) {
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
    var order_id: String?=null
    var mMyordersAdapter: Order_listingAdapter? =null
    private var orderitemlist:MutableList<Orderlist> = ArrayList<Orderlist>()
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var ordersPresenter: OrdersPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_listing)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        loadingDialog= LoadingDialog(this)
        ordersPresenter=OrdersPresenter(this)
        if(intent.extras!=null&& intent.extras!!.containsKey("order_id")){
            order_id=intent.getStringExtra("order_id")
        }

         ordersPresenter.getOrderProducts(order_id)

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
        mMyordersAdapter=Order_listingAdapter (orderitemlist,object : Order_listingAdapter.OnchildCallBack{
            override fun onChildClick(position: Int, mChildrenList: MutableList<Children>) {

            }

            override fun onServiceClickCallBack(position: Int) {
            }


        })


        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myOders_list_RV .layoutManager = horizontalLayoutManager
        myOders_list_RV.adapter = mMyordersAdapter


    }
    private fun initToolBar() {
        setSupportActionBar(orders_list_Toolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text =  getString(R.string.my_orders)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}


