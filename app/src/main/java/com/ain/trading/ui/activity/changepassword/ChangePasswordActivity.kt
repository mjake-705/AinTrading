package com.ain.trading.ui.activity.changepassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.ain.trading.R
import com.ain.trading.api.DeleteAddressResponse
import com.ain.trading.api.PasswordChange
import com.ain.trading.ui.activity.login.AccountActivity
import com.ain.trading.ui.fragments.fragment_SignIn
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.checkNetworkConnectivity
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.app_bar_center_title.*

class ChangePasswordActivity : AppCompatActivity(),ChangePasswordView {
    override fun onForgotPasswordSuccess(body: DeleteAddressResponse?) {
    }

    override fun onPasswordChangeSuccess(body: PasswordChange?) {
      //
/*
finish()*/
        if(body?.StatusCode==6000) {
            Toast.makeText(this,body?.message, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AccountActivity::class.java))
            finish()
        }
        else if(body?.StatusCode==6001){
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
    private lateinit var changePasswordPresenter: ChangePasswordPresenter
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initToolBar()
        loadingDialog = LoadingDialog(activity = this)
        changePasswordPresenter= ChangePasswordPresenter(this)

        submit.setOnClickListener {
            if (this?.checkNetworkConnectivity()!!) {
                when {
                    old_pass_Tv.text.toString()?.trim().isEmpty() -> {
                        old_pass_Tv.error ="enter old password"
                        old_pass_Tv.requestFocus()
                    }
                    new_pass_Tv.text.toString()?.trim().isEmpty() -> {
                        new_pass_Tv.error ="enter new password"
                        new_pass_Tv.requestFocus()
                    }
                    confirm_new_pass_Tv.text.toString()?.trim().isEmpty() -> {
                        confirm_new_pass_Tv.error ="please re enter your password"
                        confirm_new_pass_Tv.requestFocus()
                    }
                    new_pass_Tv.text?.toString()!=confirm_new_pass_Tv.text?.toString() ->
                    {
                        //  this?.showSnackBar(getString(R.string.not_match))
                        Toast.makeText(this,"password desnot match", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        changePasswordPresenter.ChangePassword(old_pass_Tv.text.toString(),new_pass_Tv.text.toString(),confirm_new_pass_Tv.text.toString())
                    }
                }
            }
        }

    }

    private fun initToolBar() {
        setSupportActionBar(change_paswrdToolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text =getString(R.string.change_paswrd)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}
