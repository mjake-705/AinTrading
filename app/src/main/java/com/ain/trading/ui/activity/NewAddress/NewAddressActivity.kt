package com.ain.trading.ui.activity.NewAddress

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
import com.ain.trading.utils.CustomListCountryDialog
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.checkNetworkConnectivity
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_parent_subcategory.*
import kotlinx.android.synthetic.main.app_bar_center_title.*

class NewAddressActivity : AppCompatActivity(),AddressView {
    override fun onCountryRegionFetched(body: StateResponse?) {
        countryList.clear()
        if (body?.StatusCode==6000) {
            countryList.addAll(body?.data)
            cityAddress_id.setEnabled(true);
        }

    }

    override fun onAddressAddingSuccesss(body: AddAddress?) {
        if (body?.StatusCode == 6000) {
            Toast.makeText(this,body?.message,Toast.LENGTH_SHORT).show()
            disableField()
            startActivity(Intent(applicationContext, MyAddressActivity::class.java))
            finish()
        }
        else if(body?.StatusCode==6001)
        {
            Toast.makeText(this,body?.message,Toast.LENGTH_SHORT).show()
        }


    }

    private fun disableField() {

        name_address_id.isEnabled = false
        mobileNoAdress_id.isEnabled = false
        pincodeAddress_id.isEnabled = false
        addressPerson_id.isEnabled = false
        townAddress_id.isEnabled = false
        cityAddress_id.isEnabled = false
        stateAddress_id.isEnabled = false
        home_radio_button.isEnabled = false
        office_radio_button.isEnabled = false

    }


    override fun onCountryCityFetched(body: CountryCity?) {
        citiesList.clear()
        if (body?.StatusCode==6000) {
            citiesList.addAll(body?.data)
            cityAddress_id.setEnabled(true);
        }
    }
    protected fun hideKeyboard(view: View) {
        val `in` = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
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
    var state_id: Int? = null
    var city_id: String? = null
    private var selectedZone:States?=null
    private var selectedcity: Cities?=null
    var typeOfAddressValue : String? = null
    var defaultValue : Int? = 0
    private var countryList:MutableList<States> = ArrayList<States>()
    private var citiesList:MutableList<Cities> = ArrayList<Cities>()
    private lateinit var pickZone: CustomListCountryDialog<States>

    private lateinit var pickCity: CustomListCountryDialog<Cities>
    private lateinit var addressBookPresenter: AddressPresenter
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        loadingDialog = LoadingDialog(activity = this)
        pickZone = CustomListCountryDialog(this)
        pickCity = CustomListCountryDialog(this)
        addressBookPresenter = AddressPresenter(this)
        val country_id: String? = "101"
        addressBookPresenter.getRegion(country_id)

      initToolBar()
        newAddressCL.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(view:View, ev:MotionEvent):Boolean {
                hideKeyboard(view)
                return false
            }
        })

        radioAddress.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio) {
                home_radio_button -> {
                    typeOfAddressValue = "1"
                }


                office_radio_button -> {
                    typeOfAddressValue = "2"
                }
            }
        }

        mydefaultadrsCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            defaultValue = 1
        }

        stateAddress_id.setOnClickListener {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            dialog.setCancelable(true)
            pickZone.show(countryList,
                object : CustomListCountryDialog.OnListItemClickListener<States> {
                    override fun onListItemClicked(item: States) {
                        selectedZone = item
                        state_id = selectedZone?.state_id
                        stateAddress_id.setText(selectedZone?.state_name)
                        addressBookPresenter.getSubRegion(state_id.toString())
                        stateAddress_id.setTextColor(resources.getColor(R.color.colorAccent))

                    }


                })

        }

        cityAddress_id.setOnClickListener {
            if (stateAddress_id.text.toString().trim().isNullOrEmpty()) {
                Toast.makeText(this,getString(R.string.please_state),Toast.LENGTH_LONG).show()
            } else if(!stateAddress_id.text.toString().trim().isNullOrEmpty()) {
                cityAddress_id.setEnabled(true)
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
                            city_id = selectedcity?.city_id.toString()
                            cityAddress_id.setText(selectedcity?.city_name)

                            cityAddress_id.setTextColor(resources.getColor(R.color.colorAccent))

                        }


                    })

            }
        }

        new_address_saveBaseCL.setOnClickListener {
            if (this?.checkNetworkConnectivity()!!) {

                when {
                    name_address_id.getText().toString().isEmpty() -> {
                        name_address_id.error = getString(R.string.your_first)
                        name_address_id.requestFocus()
                    }
                    mobileNoAdress_id.getText().toString().isEmpty() -> {
                        mobileNoAdress_id.error = getString(R.string.entermobileNo)
                        mobileNoAdress_id.requestFocus()
                    }

                    pincodeAddress_id.getText().toString().isEmpty() -> {
                        pincodeAddress_id.error = getString(R.string.entermobileNo)
                        pincodeAddress_id.requestFocus()
                    }

                    addressPerson_id.getText().toString().isEmpty() -> {
                        addressPerson_id.error = getString(R.string.entermobileNo)
                        addressPerson_id.requestFocus()
                    }

                    townAddress_id.getText().toString().isEmpty() -> {
                        townAddress_id.error = getString(R.string.entermobileNo)
                        townAddress_id.requestFocus()
                    }
                    cityAddress_id.getText().toString().isEmpty() -> {
                        cityAddress_id.error = getString(R.string.entermobileNo)
                        cityAddress_id.requestFocus()
                    }
                    stateAddress_id.getText().toString().isEmpty() -> {
                        stateAddress_id.error = getString(R.string.entermobileNo)
                        stateAddress_id.requestFocus()
                    }

                  radioAddress.getCheckedRadioButtonId() == -1 ->
                    {
                        office_radio_button.error = getString(R.string.entermobileNo)
                        radioAddress.requestFocus()
                    }

                else -> {


                    addressBookPresenter.addressAdding(
                        name_address_id.text.toString(),
                        mobileNoAdress_id.text.toString(),
                        addressPerson_id.text.toString(),
                        typeOfAddressValue.toString(),
                        stateAddress_id.text.toString(),
                        pincodeAddress_id.text.toString(),
                        city_id.toString(),
                        defaultValue.toString()

                    )


                   // startActivity(Intent(applicationContext, AddressDetailActivity::class.java))

                }

                }
        }
    }

        newaddress_cancelBaseCL.setOnClickListener {

            finish()
        }

    }
    private fun initToolBar() {
        setSupportActionBar(new_addressToolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text =  getString(R.string.add_new_addrs)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}
