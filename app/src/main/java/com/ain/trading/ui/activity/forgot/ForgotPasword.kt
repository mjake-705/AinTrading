package com.ain.trading.ui.activity.forgot

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.ain.trading.R
import com.ain.trading.api.DeleteAddressResponse
import com.ain.trading.api.PasswordChange
import com.ain.trading.ui.activity.changepassword.ChangePasswordPresenter
import com.ain.trading.ui.activity.changepassword.ChangePasswordView
import com.ain.trading.ui.activity.login.AccountActivity
import com.ain.trading.utils.AinUtil
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.checkNetworkConnectivity
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_forgot_pasword.*
import kotlinx.android.synthetic.main.app_bar_center_title.*

class ForgotPasword : AppCompatActivity(),ChangePasswordView {
    override fun onForgotPasswordSuccess(body: DeleteAddressResponse?) {
        if(body?.StatusCode==6000) {
            Toast.makeText(this,body?.message, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AccountActivity::class.java))
            finish()
        }
        else if(body?.StatusCode==6001){
            Toast.makeText(this,body?.message, Toast.LENGTH_SHORT).show()

        }
    }

    override fun onPasswordChangeSuccess(body: PasswordChange?) {
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

    private lateinit var changePasswordPresenter: ChangePasswordPresenter
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pasword)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initToolBar()
        forgot_top_cl.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(view:View, ev: MotionEvent):Boolean {
                hideKeyboard(view)
                return false
            }
        })
        loadingDialog = LoadingDialog(activity = this)
        changePasswordPresenter= ChangePasswordPresenter(this)
        forgot_buton_id.setOnClickListener {
            if (this?.checkNetworkConnectivity()!!) {
               when{

                   forgotemil_id.text.toString()?.trim().isEmpty() -> {
                       forgotemil_id.error ="enter old password"
                       forgotemil_id.requestFocus()
                   }
                       !AinUtil.isEmailValid(forgotemil_id.text.toString().trim()) -> {
                           forgotemil_id.error = getString(R.string.valid_email)
                           forgotemil_id.requestFocus()
                       }
                   else ->{
                       changePasswordPresenter.ForgotPassword(forgotemil_id.text.toString())

                   }
               }

            }

        }
    }

    private fun initToolBar() {
        setSupportActionBar(forgot_paswrdToolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
     //   supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text = getString(R.string.forgot)
    }
}
