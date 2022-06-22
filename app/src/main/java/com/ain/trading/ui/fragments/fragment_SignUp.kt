package com.ain.trading.ui.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.ain.trading.R
import com.ain.trading.api.LoginResponse
import com.ain.trading.api.RegistrationResponse
import com.ain.trading.ui.activity.otp.OTP_Screen_Activity
import com.ain.trading.ui.activity.login.AccountPresenter
import com.ain.trading.ui.activity.login.AccountView
import com.ain.trading.utils.AinPref
import com.ain.trading.utils.AinUtil
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.frag_signup.*

class fragment_SignUp : Fragment(), AccountView {
    override fun onLoginSuccess(body: LoginResponse?) {
     activity?.showSnackBar(body?.message.toString())
    }

    override fun onRegistrationSuccess(body: RegistrationResponse?) {
        if(body?.StatusCode==6000) {
         //username_Tv   AinPref.setAuthToken(body?.data?.auth_token.toString())
            AinPref.setPhone(body?.data?.number.toString())
            AinPref.setRegistrationId(body?.data?.id?.toString())
            activity?.showSnackBar(body?.message)
            startActivity(Intent(activity, OTP_Screen_Activity::class.java))
            activity?.finish()


        }
        else if((body?.StatusCode==6001)){
            Toast.makeText(activity,body?.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(error: String?) {
        activity?.showSnackBar(error.toString())
    }

    override fun showProgress() {
        loadingDialog.show()
    }

    override fun hideProgress() {
        loadingDialog.cancel()
    }
    protected fun hideKeyboard(view: View) {
        val `in` = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    var user_type_id: Int?=2
    val passwordPattern:String= "(?=^.{8,}$)((?=.*\\d)(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$"
    var telephonyManager: TelephonyManager?=null
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var accountPresenter: AccountPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.frag_signup, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadingDialog = LoadingDialog(activity = activity)
        accountPresenter = AccountPresenter(this)

        mainCL.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(view:View, ev: MotionEvent):Boolean {
                hideKeyboard(view)
                return false
            }
        })
        vipLoginCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            user_type_id = 1
        }
       // deviceId()
        sign_up_id.setOnClickListener {
            when {

                first_name_edit_id.text.toString().isEmpty() -> {
                    first_name_edit_id.error = getString(R.string.your_first)
                    first_name_edit_id.requestFocus()
                }
                emil_edit_id.text.toString().isEmpty() -> {
                    emil_edit_id.error = getString(R.string.last_name)
                    emil_edit_id.requestFocus()
                }

                lastname_edit_id.text.toString().isEmpty() -> {
                    lastname_edit_id.error = getString(R.string.your_email)
                    lastname_edit_id.requestFocus()
                }
                mobile_edit_id.text.toString().isEmpty() -> {
                    mobile_edit_id.error = getString(R.string.your_mobile)
                    mobile_edit_id.requestFocus()
                }
                !AinUtil.isValidMobile(mobile_edit_id.text.toString().trim()) -> {
                    mobile_edit_id.error = getString(R.string.valid_number)
                    mobile_edit_id.requestFocus()
                }
                !AinUtil.isEmailValid(lastname_edit_id.text.toString().trim()) -> {
                    lastname_edit_id.error = getString(R.string.valid_email)
                    lastname_edit_id.requestFocus()
                }

                paswrd_edit_id.text.toString()?.trim().isEmpty() -> {
                    paswrd_edit_id.error = getString(R.string.your_password)
                    paswrd_edit_id.requestFocus()
                }
                (!paswrd_edit_id.text.toString().matches(passwordPattern.toRegex()))->
                {
                    paswrd_edit_id.error =getString(R.string.password_error)
                    paswrd_edit_id.requestFocus()
                }

                conform_edit_id.text.toString()?.trim().isEmpty() -> {
                    conform_edit_id.error = getString(R.string.confirm_password)
                    conform_edit_id.requestFocus()
                }

                else -> {if(paswrd_edit_id.text.toString()==conform_edit_id.text.toString())
                {
                    accountPresenter.UserRegistration(first_name_edit_id.text.toString(),emil_edit_id.text.toString(),mobile_edit_id.text.toString(),lastname_edit_id.text.toString(),paswrd_edit_id.text.toString(),conform_edit_id.text.toString(),user_type_id.toString())
                }
                else{
                    Toast.makeText(activity,getString(R.string.password_not_matches), Toast.LENGTH_SHORT).show()
                }

                }
            }
        }


    }

/*    private fun deviceId() {
        val permissionCheck = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_PHONE_STATE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {


            val REQUEST_READ_PHONE_STATE=1
            ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>(Manifest.permission.READ_PHONE_STATE), REQUEST_READ_PHONE_STATE)
        }
        else
        {
//TODO
        }
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_PHONE_STATE) !== PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>(Manifest.permission.READ_PHONE_STATE), 101)
            return
        }
    }*/



    override fun onRequestPermissionsResult(requestCode:Int, permissions:Array<String>, grantResults:IntArray) {
        when (requestCode) {
            101 -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_PHONE_STATE) !== PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>(Manifest.permission.READ_PHONE_STATE), 101)
                    return
                }
                val imeiNumber = telephonyManager?.getDeviceId()
                Toast.makeText(activity, imeiNumber, Toast.LENGTH_LONG).show()
            }
            else
            {
                Toast.makeText(activity, "Without permission we check", Toast.LENGTH_LONG).show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}