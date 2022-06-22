package com.ain.trading.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ain.trading.R
import com.ain.trading.ui.activity.home.HomeActivity
import com.ain.trading.ui.activity.language.SelectLanguageActivity
import com.ain.trading.ui.activity.login.AccountActivity
import com.ain.trading.utils.AinPref
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*

class Splash_Activity : AppCompatActivity() {
    internal lateinit var handler: Handler
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (AinPref.getAuthToken().toString().isNotEmpty()) {
         /*   startActivity(Intent(this, HomeActivity::class.java))
            finish()*/
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("msg", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

// Get new Instance ID token
                    val token = task.result?.token

// Log and toast
                    val msg = getString(R.string.msg_token_fmt, token)
                  //  Log.d(TAG, msg)
                 //   Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                })
            FirebaseMessaging.getInstance().isAutoInitEnabled = true
            if (AinPref.getOnlanguageIsSet()) {
                Handler().postDelayed({
                    if (AinPref.getLanguageId() == "2") {
                        val locale = Locale("ar")
                        Locale.setDefault(locale)
                        val config = Configuration()
                        config.locale = locale
                        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
                        startActivity(Intent(this, HomeActivity::class.java))
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish()
                    } else if (AinPref.getLanguageId() == "1") {
                        val locale = Locale("en")
                        Locale.setDefault(locale)
                        val config = Configuration()
                        config.locale = locale
                        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
                        startActivity(Intent(this, HomeActivity::class.java))
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish()

                    }
                }, 2000)
            } else {

                startActivity(Intent(this, SelectLanguageActivity::class.java))
                finish()
            }
        } else {
            if (!AinPref.getOnlanguageIsSet()){

                Handler().postDelayed({
                    startActivity(Intent(this, SelectLanguageActivity::class.java))
                    finish()
                }, 2000)
            }
            else{
                startActivity(Intent(this, AccountActivity::class.java))
                finish()
        }

        }
     /*   handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(this@Splash_Activity, AccountActivity::class.java)
            startActivity(intent)
            finish()
        }, 6000)*/
    }
}
