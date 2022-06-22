package com.ain.trading.ui.activity.language

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.ain.trading.R
import com.ain.trading.ui.activity.login.AccountActivity
import com.ain.trading.utils.AinPref
import kotlinx.android.synthetic.main.activity_select_language.*
import java.util.*

class SelectLanguageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        left_Cl.setOnClickListener {
            AinPref.setLanguageId("1")
            AinPref.setOnlanguageIsSet(true)

            val locale = Locale("en")
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
            startActivity(Intent(this, AccountActivity::class.java))
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish()
        }
        rgt_CL.setOnClickListener {
            AinPref.setLanguageId("2")
            AinPref.setOnlanguageIsSet(true)
            /* val languageToLoad = "ar"
             val locale = Locale(languageToLoad)
             Locale.setDefault(locale)
             val config = Configuration()
             config.locale = locale
             this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics())*/

            val locale = Locale("ar")
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
            startActivity(Intent(this, AccountActivity::class.java))
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish()
        }



    }
}
