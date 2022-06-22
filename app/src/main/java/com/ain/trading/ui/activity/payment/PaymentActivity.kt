package com.ain.trading.ui.activity.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.AddressDetailAdapter
import com.ain.trading.adapter.PaymentTypeAdapter
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.AddressDetail.AddressDetailActivityPresenter
import com.ain.trading.ui.activity.AddressDetail.AddressDetailView
import com.ain.trading.ui.activity.MyAddress.MyAddressActivity
import com.ain.trading.ui.activity.card.CardWebViewActivity
import com.ain.trading.utils.AinPref
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.checkNetworkConnectivity
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_address_detail.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.app_bar_center_title.*

class PaymentActivity : AppCompatActivity(),PaymentView, AddressDetailView {
    override fun onPaymentTypeFetched(body: PaymentTypeResponse?) {
        mPaymentTypeResponseData?.clear()
        if (body?.StatusCode==6000) {
            mPaymentTypeResponseData?.addAll(body?.data)
            mPaymentTypeAdapter?.notifyDataSetChanged()
        }
        else if(body?.StatusCode==6001)
        {
            Toast.makeText(this,body?.message,Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCartFetched(body: GetCartlistResponse?) {
        order_prize_id.text=body?.data?.grand_total+" SAR"
        //    deliverychargePriceTv.text=body?.data?.grand_total
      //  itemsTotal_price_tv.text=body?.data?.grand_total
        totl_prize_id.text=body?.data?.grand_total+" SAR"

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

            name_id.text = (body?.data?.address_holder)
            module_id.text = (body?.data?.primary_address)
            gayatri_id.text = (body?.data?.location)
            city_payment.text = (body?.data?.city_name)
            city_id.text = (body?.data?.state_name)
            mob_id.text = (body?.data?.primary_number)
            customer_to_address_id = body?.data?.customer_to_address_id.toString()
            if(body?.data?.address_type_id=="1")
            {
                home_id.text=getString(R.string.home)
            }
            else if(body?.data?.address_type_id=="2"){
                home_id.text=getString(R.string.office)
            }

        }
    }

    override fun onPaySuccesfully(body: PaymentResponse?) {
      if(body?.StatusCode==6000)
      {          url=body?.data
          startActivity(Intent(applicationContext,CardWebViewActivity::class.java).putExtra("url",url))

      }
        else if(body?.StatusCode==6001)
      {
          Toast.makeText(this,body?.message, Toast.LENGTH_SHORT).show()
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
    var mPaymentTypeResponseData: MutableList<PaymentTypeResponseData> = ArrayList<PaymentTypeResponseData>()
    var mPaymentTypeAdapter: PaymentTypeAdapter? =null
    var mCarttList: MutableList<Item> = ArrayList<Item>()
    var customer_to_address_id: String?=null
    var paymentmode: String?=null
    var url: String?=null
    var shipping_rate: String?=null
    var mAddressDetailAdapter: AddressDetailAdapter? =null
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var paymentPresenter: PaymentPresenter
    private lateinit var mAddressDetailActivityPresenter: AddressDetailActivityPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        loadingDialog= LoadingDialog(this)
        mAddressDetailActivityPresenter = AddressDetailActivityPresenter(this)
        mAddressDetailActivityPresenter.defaultAddress()
        paymentPresenter= PaymentPresenter(this)
        if (this?.checkNetworkConnectivity()!!) {

            paymentPresenter.getpaymentType()


        }
        if(intent.extras!=null&& intent.extras!!.containsKey("customer_to_address_id")){
            customer_to_address_id=intent.getStringExtra("customer_to_address_id")
        }
        if(intent.extras!=null&& intent.extras!!.containsKey("shipping_rate")){
            shipping_rate=intent.getStringExtra("shipping_rate")
            rupes_id.text=shipping_rate+" SAR"
        }

        if(AinPref.getGuestUser().equals("3"))
        {

        }
        else if(AinPref.getGuestUser().equals("0")){
            mAddressDetailActivityPresenter.getCart()
        }
        initToolBar()
        initiCartIemsVp()
        chnge_adrs_id.setOnClickListener {
            startActivity(Intent(applicationContext,MyAddressActivity::class.java))
        }





        paynw_id.setOnClickListener {

            paymentPresenter.getPayment(customer_to_address_id.toString(),paymentmode.toString(),shipping_rate)
        }






    }
    private fun initiCartIemsVp() {
        val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.menshirts))
        mPaymentTypeAdapter= PaymentTypeAdapter(mPaymentTypeResponseData,object : PaymentTypeAdapter.OnchildCallBack{
            override fun onChildClick(position: Int, pmode: String) {
            paymentmode=pmode
            }

            override fun onEmptyClick(position: Int) {
            }


        })



        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        payment_type_Rv.layoutManager = horizontalLayoutManager
        payment_type_Rv.adapter = mPaymentTypeAdapter




    }
    private fun initToolBar() {
        setSupportActionBar(payment_toolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text =getString(R.string.payment)



    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
