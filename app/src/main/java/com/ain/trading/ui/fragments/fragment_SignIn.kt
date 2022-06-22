package com.ain.trading.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.ain.trading.R
import com.ain.trading.api.LoginResponse
import com.ain.trading.api.RegistrationResponse
import com.ain.trading.ui.activity.forgot.ForgotPasword
import com.ain.trading.ui.activity.home.HomeActivity
import com.ain.trading.ui.activity.login.AccountPresenter
import com.ain.trading.ui.activity.login.AccountView
import com.ain.trading.utils.*
import kotlinx.android.synthetic.main.frag_signin.*

class fragment_SignIn : Fragment(), AccountView {
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var accountPresenter: AccountPresenter
    override fun onRegistrationSuccess(body: RegistrationResponse?) {
    }

    override fun onLoginSuccess(body: LoginResponse?) {
        if (body?.StatusCode == 6000) {
            AinPref.setAuthToken(body?.data?.auth_token.toString())
            AinPref.setGuestUser("0")
            startActivity(Intent(activity, HomeActivity::class.java))
            activity?.finish()
        } else if ((body?.StatusCode == 6001)) {
            Toast.makeText(activity, body?.message, Toast.LENGTH_SHORT).show()

        }
    }

    override fun onError(error: String?) {
        activity?.showSnackBar("Sorry ,Unauthorised Login Password")


    }
    protected fun hideKeyboard(view: View) {
        val `in` = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun showProgress() {
        loadingDialog.show()
    }

    override fun hideProgress() {
        loadingDialog.cancel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.frag_signin, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingDialog = LoadingDialog(activity = activity)
        accountPresenter = AccountPresenter(this)
        top_Cl.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(view:View, ev: MotionEvent):Boolean {
                hideKeyboard(view)
                return false
            }
        })
        guest_user_Tv.setOnClickListener {
            if (activity?.checkNetworkConnectivity()!!) {
                AinPref.setGuestUser("3")
                startActivity(Intent(activity, HomeActivity::class.java))
            }
        }
        forgot_id.setOnClickListener {
            startActivity(Intent(activity, ForgotPasword::class.java))
            activity?.finish()
        }
        sign_buton_id.setOnClickListener {

            if (activity?.checkNetworkConnectivity()!!) {
                when {
                    !AinUtil.isEmailValid(emil_id.text.toString().trim()) -> {
                        emil_id.error = getString(R.string.valid_email)
                        emil_id.requestFocus()
                    }

                    password_id.text.toString()?.trim().isEmpty() -> {
                        password_id.error = getString(R.string.your_password)
                        password_id.requestFocus()
                    }
/*(!password_Tv.text.toString().matches(passwordPattern.toRegex())) -> {
password_Tv.error = getString(R.string.password_error)
password_Tv.requestFocus()
}*/
                    else -> {
                        accountPresenter.UserLogin(emil_id.text.toString(), password_id.text.toString(),
                            AinPref.getCustomerId().toString()
                        )
                    }


                }
            }

/* startActivity(Intent(activity, HomeActivity::class.java))
activity?.finish()*/
        }


    }


}