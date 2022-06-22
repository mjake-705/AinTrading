package com.ain.trading.ui.activity.card

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.ain.trading.R
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ain.trading.ui.activity.home.HomeActivity
import kotlinx.android.synthetic.main.activity_card_web_view.*
import android.webkit.WebChromeClient




class CardWebViewActivity : AppCompatActivity() {
    var url: String?=null
    var webView: WebView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_card_web_view)
        if(intent.extras!=null&& intent.extras!!.containsKey("url")){ url=intent.getStringExtra("url") }

        webView = findViewById(R.id.help_webview) as WebView
        webView?.settings?.javaScriptEnabled = true
        webView?.webViewClient = WebViewClient()
        webView?.setWebChromeClient(WebChromeClient())
        webView?.loadUrl(url)
        cancel_payment.setOnClickListener {
            val i = Intent(this, HomeActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }


    override fun onBackPressed() {
    }

}
