package com.ain.trading.ui.activity.EditAddress

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.ain.trading.R
import com.ain.trading.api.*
import com.ain.trading.ui.activity.AddressDetail.AddressDetailActivity
import com.ain.trading.ui.activity.MyAddress.MyAddressActivity
import com.ain.trading.ui.activity.MyAddress.MyAddressView
import com.ain.trading.ui.activity.MyAddress.MyaddressPresenter
import com.ain.trading.ui.activity.NewAddress.AddressPresenter
import com.ain.trading.ui.activity.NewAddress.AddressView
import com.ain.trading.utils.CustomListCountryDialog
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_edit_address.*

import kotlinx.android.synthetic.main.app_bar_center_title.*

class EditAddressActivity : AppCompatActivity(),EditAddressView, AddressView {
    override fun setFindAddressSuccesss(body: FindAddressResponse?) {
        edit_name_address_id.setText(body?.data?.address_holder)
        edit_mobileNoAdress_id.setText(body?.data?.primary_number)
        edit_pincodeAddress_id.setText(body?.data?.postal_code.toString())
        edit_addressPerson_id.setText(body?.data?.primary_address)
        edit_townAddress_id.setText(body?.data?.location)
        edit_cityAddress_id.setText(body?.data?.city_name)
        edit_stateAddress_id.setText(body?.data?.state_name)
        state_id= body?.data?.state_id?.toString()
        city_id=body?.data?.city_id?.toString()
        //edit_home_radio_button.isSelected
        if(body?.data?.address_type_id?.equals("1")!!) {
            edit_radioAddress.check(edit_home_radio_button.getId());
        }
        else if(body?.data?.address_type_id?.equals("2")){
            edit_radioAddress.check(edit_office_radio_button.getId());
        }

            if(body?.data?.default?.equals(1)!!) {

            edit_mydefaultadrsCheckBox.setChecked(true);
        }
        else{
            edit_mydefaultadrsCheckBox.setChecked(false);
        }


    }


    override fun onEditAddressSuccesss(body: EditAddressResponse?) {
     if(body?.StatusCode==6000)
     {
         Toast.makeText(this,body?.message, Toast.LENGTH_SHORT).show()
             finish()

     }
        else if(body?.StatusCode==6001)
     {
         Toast.makeText(this,body?.message, Toast.LENGTH_SHORT).show()
     }
    }

    override fun setDefaulttAddressSuccesss(body: EditAddressResponse?) {

    }


    override fun onCountryRegionFetched(body: StateResponse?) {
        countryList.clear()
        if (body?.StatusCode==6000) {
            countryList.addAll(body?.data)

        }
        else if(body?.StatusCode==6001){
            Toast.makeText(this,body?.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCountryCityFetched(body: CountryCity?) {
        citiesList.clear()
        if (body?.StatusCode==6000) {
            citiesList.addAll(body?.data)
            showcityDialog()
        }
        else if(body?.StatusCode==6001){
            Toast.makeText(this,body?.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun showcityDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        dialog.setCancelable(true)
        pickCity.show(citiesList,
            object : CustomListCountryDialog.OnListItemClickListener<Cities> {
                override fun onListItemClicked(item: Cities) {
                    selectedcity = item
                     city_id = selectedcity?.city_id?.toString()
                    edit_cityAddress_id.setText(selectedcity?.city_name)

                    edit_cityAddress_id.setTextColor(resources.getColor(R.color.colorAccent))

                }


            })
    }

    override fun onAddressAddingSuccesss(body: AddAddress?) {

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

    protected fun hideKeyboard(view: View) {
        val `in` = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
    private fun disableField() {

         edit_name_address_id.isEnabled = false
         edit_mobileNoAdress_id.isEnabled = false
         edit_pincodeAddress_id.isEnabled = false
         edit_addressPerson_id.isEnabled = false
         edit_townAddress_id.isEnabled = false
         edit_cityAddress_id.isEnabled = false
         edit_stateAddress_id.isEnabled = false
         edit_home_radio_button.isEnabled = false
         edit_office_radio_button.isEnabled = false

    }
    var city_id:String? = null
    var customer_to_address_id: String?=null
    var state_id:String? = null
    var from:String? = null
    private var selectedZone:States?=null
    private var selectedcity: Cities?=null
    var typeOfAddressValue : String? = null
    var defaultValue : Int? = 0
    private var countryList:MutableList<States> = ArrayList<States>()
    private var citiesList:MutableList<Cities> = ArrayList<Cities>()
    private lateinit var pickZone: CustomListCountryDialog<States>
    private lateinit var mMyaddressPresenter: MyaddressPresenter
    private lateinit var pickCity: CustomListCountryDialog<Cities>
    private lateinit var addressBookPresenter: AddressPresenter
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var editAddressPresenter: EditAddressPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        loadingDialog = LoadingDialog(activity = this)
        pickZone = CustomListCountryDialog(this)
        pickCity= CustomListCountryDialog(this)

        initToolBar()
        main_Cl.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(view:View, ev:MotionEvent):Boolean {
                hideKeyboard(view)
                return false
            }
        })

        addressBookPresenter = AddressPresenter(this)
        editAddressPresenter = EditAddressPresenter(this)
        val country_id: String? = "101"
        addressBookPresenter.getRegion(country_id)

        if(intent.extras!=null&& intent.extras!!.containsKey("customer_to_address_id")){
            customer_to_address_id=intent.getStringExtra("customer_to_address_id")
        }

        editAddressPresenter.findparticularAddress(customer_to_address_id)


        edit_radioAddress.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio) {
                edit_home_radio_button -> {
                    typeOfAddressValue = "1"
                }


                edit_office_radio_button -> {
                    typeOfAddressValue = "2"
                }
            }
        }
        edit_mydefaultadrsCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                defaultValue = 1
            }
            else{
                defaultValue = 0
            }
        }

        edit_stateAddress_id.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            dialog.setCancelable(true)
            pickZone.show(countryList,
                object : CustomListCountryDialog.OnListItemClickListener<States> {
                    override fun onListItemClicked(item: States) {
                        selectedZone = item
                        state_id = selectedZone?.state_id.toString()
                        edit_stateAddress_id.setText(selectedZone?.state_name)
                  //      addressBookPresenter.getSubRegion(state_id.toString())
                        edit_stateAddress_id.setTextColor(resources.getColor(R.color.colorAccent))
                        edit_cityAddress_id.text="Select city"

                    }
                })
        }

        edit_cityAddress_id.setOnClickListener {
           /* if (edit_stateAddress_id.text.toString().trim().isNullOrEmpty()){
                edit_cityAddress_id.setEnabled(false);
            }
            else {*/
            //    edit_cityAddress_id.setEnabled(true)
            addressBookPresenter.getSubRegion(state_id.toString())

       //     }

        }

        edit_new_address_saveBaseCL.setOnClickListener {
            if(edit_cityAddress_id.text=="Select city"){
                Toast.makeText(this,"select city", Toast.LENGTH_SHORT).show()
            }
else{
            editAddressPresenter.editaddresslist(
                edit_name_address_id.text.toString(),
                edit_mobileNoAdress_id.text.toString(),
                edit_addressPerson_id.text.toString(),
                typeOfAddressValue.toString(),
                edit_stateAddress_id.text.toString(),
                edit_pincodeAddress_id.text.toString(),
                city_id.toString(),
                defaultValue.toString(), customer_to_address_id.toString()
            )
        }}
        edit_newaddress_cancelBaseCL.setOnClickListener {
            finish()
        }
    }
    private fun initToolBar() {
        setSupportActionBar(edit_aboutToolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text = getString(R.string.edit_address)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    }





