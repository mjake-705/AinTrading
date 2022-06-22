package com.ain.trading.ui.activity.AddressDetail

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
import com.ain.trading.adapter.AddressDetailAdapter
import com.ain.trading.adapter.MyAddressAdapter
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.NewAddress.NewAddressActivity
import com.ain.trading.ui.activity.EditAddress.EditAddressActivity
import com.ain.trading.ui.activity.MyAddress.MyAddressActivity
import com.ain.trading.ui.activity.MyAddress.MyAddressView
import com.ain.trading.ui.activity.MyAddress.MyaddressPresenter
import com.ain.trading.ui.activity.payment.PaymentActivity
import kotlinx.android.synthetic.main.activity_address_detail.*
import kotlinx.android.synthetic.main.activity_parent_subcategory.*
import kotlinx.android.synthetic.main.app_bar_center_title.*
import android.app.Activity
import com.ain.trading.utils.AinPref
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.address_details_items.*


class AddressDetailActivity : AppCompatActivity(),AddressDetailView {
    override fun onCartFetched(body: GetCartlistResponse?) {
        items_price_tv.text=body?.data?.grand_total
    //    deliverychargePriceTv.text=body?.data?.grand_total
        itemsTotal_price_tv.text=body?.data?.grand_total
        addressPrice_Tv.text=body?.data?.grand_total+" SAR"

        if (body?.StatusCode==6000) {
            mCarttList?.addAll(body?.data?.items)
            mAddressDetailAdapter?.notifyDataSetChanged()

        }
        else if (body?.StatusCode==6001)
        {

        }
    }

    override fun showdefaultAddressSuccesss(body: DefaultAddressResponse?) {

        if(body?.data!=null) {
            add_new_Cl.visibility= View.GONE
            baseAddressCL.visibility= View.VISIBLE
            name_Person.text = (body?.data?.address_holder)
            primaryaddress.text = (body?.data?.primary_address)
            locationAddress.text = (body?.data?.location)
            cityOfAddress.text = (body?.data?.city_name)
            stateOfAddress.text = (body?.data?.state_name)
            mobileNo_tv.text = (body?.data?.primary_number)
            customer_to_address_id = body?.data?.customer_to_address_id.toString()
            if(body?.data?.address_type_id=="1")
            {
                home_addrs_tv.text=getString(R.string.home)
            }
            else if(body?.data?.address_type_id=="2"){
                home_addrs_tv.text=getString(R.string.office)
            }

        }
        else{
            add_new_Cl.visibility= View.VISIBLE
            baseAddressCL.visibility= View.GONE
            customer_to_address_id=""
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

    private fun disableField() {

        name_Person.isEnabled = false
        primaryaddress.isEnabled = false
        locationAddress.isEnabled = false
        cityOfAddress.isEnabled = false
        stateOfAddress.isEnabled = false
        mobileNo_tv.isEnabled = false
    }
    private lateinit var loadingDialog: LoadingDialog
    var mCarttList: MutableList<Item> = ArrayList<Item>()
    var customer_to_address_id:String?=null
    var shipping_rate:String?=null
    private lateinit var mAddressDetailActivityPresenter: AddressDetailActivityPresenter
    var mMyAddressAdapter: MyAddressAdapter? =null
    var maddressList: MutableList<DefaultAddress> = ArrayList<DefaultAddress>()

    var mAddressDetailAdapter: AddressDetailAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_detail)
        loadingDialog = LoadingDialog(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initToolBar()
        mAddressDetailActivityPresenter = AddressDetailActivityPresenter(this)
        mAddressDetailActivityPresenter.defaultAddress()
        if(intent.extras!=null&& intent.extras!!.containsKey("shipping_rate")){
            shipping_rate=intent.getStringExtra("shipping_rate")
        }
        addressSaveBaseCL.setOnClickListener {
             if(customer_to_address_id.toString().isNotBlank()) {
                 startActivity(
                     Intent(
                         applicationContext,
                         PaymentActivity::class.java
                     ).putExtra(
                         "customer_to_address_id",
                         customer_to_address_id
                     ).putExtra("shipping_rate", shipping_rate)
                 )
             }
            else
            {
                Toast.makeText(this,getString(R.string.not_selected), Toast.LENGTH_SHORT).show()
            }

        }
        add_new_Cl.setOnClickListener {
            startActivity(Intent(applicationContext,MyAddressActivity::class.java))
        }
        editAddressBaseCL.setOnClickListener {
      //      startActivity(Intent(applicationContext,EditAddressActivity::class.java).putExtra("customer_to_address_id", customer_to_address_id)?.putExtra("from","addressdetail"))
            val i = Intent(this, EditAddressActivity::class.java).putExtra("customer_to_address_id", customer_to_address_id)
            startActivityForResult(i, 1)

        }
        addNewAddressCL.setOnClickListener {
         //   startActivity(Intent(applicationContext,MyAddressActivity::class.java))
        val i = Intent(this, MyAddressActivity::class.java).putExtra("customer_to_address_id", customer_to_address_id)
        startActivityForResult(i, 1)
        }
        if(AinPref.getGuestUser().equals("3"))
        {

        }
        else if(AinPref.getGuestUser().equals("0")){
            mAddressDetailActivityPresenter.getCart()
        }
        initiCartIemsVp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {
            mAddressDetailActivityPresenter.defaultAddress()
        }
    }

    private fun initiCartIemsVp() {
        val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.menshirts))
        mAddressDetailAdapter= AddressDetailAdapter(mCarttList,object : AddressDetailAdapter.OnchildCallBack{
            override fun onEmptyClick(position: Int) {

            }

            override fun onChildClick(position: Int, mChildrenList: MutableList<Children>) {

            }

        })



        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        cartItemsrecyerView.layoutManager = horizontalLayoutManager
        cartItemsrecyerView.adapter = mAddressDetailAdapter




    }

    private fun initToolBar() {
        setSupportActionBar(address_head_Toolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text = getString(R.string.address)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        mAddressDetailActivityPresenter.defaultAddress()
    }



}