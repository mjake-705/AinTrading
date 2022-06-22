package com.ain.trading.ui.activity.MyAddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.MyAddressAdapter
import com.ain.trading.api.*

import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.EditAddress.EditAddressActivity
import com.ain.trading.ui.activity.EditAddress.EditAddressPresenter
import com.ain.trading.ui.activity.EditAddress.EditAddressView
import com.ain.trading.ui.activity.NewAddress.NewAddressActivity
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_edit_address.*
import kotlinx.android.synthetic.main.activity_my_address.*
import kotlinx.android.synthetic.main.app_bar_center_title.*
import kotlinx.android.synthetic.main.my_address_items.*


class MyAddressActivity : AppCompatActivity(),MyAddressView,EditAddressView {
    override fun onDeletedAddressSuccessfully(body: DeleteAddressResponse?) {
        if(body?.StatusCode==6000)
        {
            Toast.makeText(this,body?.message, Toast.LENGTH_SHORT).show()
            mMyaddressPresenter.getAddressList()
        }
        else if(body?.StatusCode==6001)
        {
            Toast.makeText(this,body?.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun setFindAddressSuccesss(body: FindAddressResponse?) {

    }

    override fun onEditAddressSuccesss(body: EditAddressResponse?) {


    }

    override fun setDefaulttAddressSuccesss(body: EditAddressResponse?) {

    }


    override fun onAddressListFetched(body: ListAddressResponse?) {
        maddressList?.clear()
        if (body?.StatusCode==6000) {
            maddressList?.addAll(body?.data)
            mMyAddressAdapter?.notifyDataSetChanged()
            no_address_Tv.visibility= View.GONE
            myaddress_topcl.visibility= View.VISIBLE
        }
        else if(body?.StatusCode==6001)
        {
            myaddress_topcl.visibility= View.GONE
            no_address_Tv.visibility= View.VISIBLE
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
    var defaultValue : Int? = 1
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var editAddressPresenter: EditAddressPresenter
    private lateinit var mMyaddressPresenter: MyaddressPresenter
    var mMyAddressAdapter: MyAddressAdapter? =null

    var maddressList: MutableList<AddressList> = ArrayList<AddressList>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_address)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        getWindow().setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        mMyaddressPresenter = MyaddressPresenter(this)
        loadingDialog = LoadingDialog(activity = this)
        mMyaddressPresenter.getAddressList()
        editAddressPresenter = EditAddressPresenter(this)
        addaddress_topcl.setOnClickListener {
            startActivity(Intent(this@MyAddressActivity, NewAddressActivity::class.java))
        }
        initToolBar()
        inititemsVp()
    }





    private fun inititemsVp() {
        val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.menshirts))
        mMyAddressAdapter= MyAddressAdapter(maddressList,object : MyAddressAdapter.OnchildCallBack{
            override fun onServiceClickCallBackDelete(
                position: Int,
                customer_to_address_id: String
            ) {
                mMyaddressPresenter.deleteAddress(customer_to_address_id)
            }

            override fun onChildClick(position: Int, customer_to_address_id: String,primary_number:String) {
                editAddressPresenter.setdefaultAddress(
                    primary_number,
                    defaultValue.toString(),
                    customer_to_address_id


                )
            }

            override fun onServiceClickCallBack(position: Int, customer_to_address_id: String) {
                val i = Intent(this@MyAddressActivity, EditAddressActivity::class.java).putExtra("customer_to_address_id", customer_to_address_id)
                startActivityForResult(i, 1)
            }




        })



        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myaddressRL.layoutManager = horizontalLayoutManager
        myaddressRL.adapter = mMyAddressAdapter




    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {
            mMyaddressPresenter.getAddressList()
        }
    }

    private fun initToolBar() {
        setSupportActionBar(addresslistToolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text =getString(R.string.adress)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }





}
