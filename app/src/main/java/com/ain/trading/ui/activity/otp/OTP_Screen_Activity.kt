package com.ain.trading.ui.activity.otp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.ain.trading.R
import com.ain.trading.api.OTPResponse
import com.ain.trading.ui.activity.login.AccountActivity
import com.ain.trading.utils.AinPref
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_otp__screen_.*

class OTP_Screen_Activity : AppCompatActivity(),OTP_View {
    override fun onOTPSuccesfully(body: OTPResponse?) {
        if(body?.StatusCode==6000)
        {
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
    protected fun hideKeyboard(view: View) {
        val `in` = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }


    private lateinit var loadingDialog: LoadingDialog
    private lateinit var mOTP_Presenter: OTP_Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp__screen_)
        otp_top_cl.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(view:View, ev: MotionEvent):Boolean {
                hideKeyboard(view)
                return false
            }
        })
        mOTP_Presenter = OTP_Presenter(this)
        loadingDialog= LoadingDialog(this)
        otptext1.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                if (otptext1.getText().toString().length == 1)
                {
                    otptext2.requestFocus()
                }
            }
            override fun beforeTextChanged(s:CharSequence, start:Int,
                                           count:Int, after:Int) {}
            override fun afterTextChanged(s: Editable) {}
        })

        otptext2.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                if (otptext2.getText().toString().length == 1)
                {
                    otptext3.requestFocus()
                }
            }
            override fun beforeTextChanged(s:CharSequence, start:Int,
                                           count:Int, after:Int) {}
            override fun afterTextChanged(s: Editable) {}
        })


        otptext3.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                if (otptext3.getText().toString().length == 1)
                {
                    otptext4.requestFocus()
                }
            }
            override fun beforeTextChanged(s:CharSequence, start:Int,
                                           count:Int, after:Int) {}
            override fun afterTextChanged(s: Editable) {}
        })

        otptext4.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s:CharSequence, start:Int, before:Int, count:Int) {
                if (otptext4.getText().toString().length == 1)
                {
                    otptext4.requestFocus()
                }
            }
            override fun beforeTextChanged(s:CharSequence, start:Int,
                                           count:Int, after:Int) {}
            override fun afterTextChanged(s: Editable) {}
        })


        otp_buton_id.setOnClickListener {
            val otp=otptext1.text.toString()+otptext2.text.toString()+otptext3.text.toString()+otptext4.text.toString()

            mOTP_Presenter.getOTP(AinPref.getPhone().toString(),otp,AinPref?.getRegistrationId().toString())
        }



    }
}
