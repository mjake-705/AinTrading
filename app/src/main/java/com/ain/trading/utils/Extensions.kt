package com.ain.trading.utils

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager.widget.ViewPager
import com.ain.trading.R
import com.google.android.material.snackbar.Snackbar

val ViewPager.lastItem: Int?
    get() = adapter?.count?.minus(1)


fun Context.isNetworkAvailable(): Boolean {

    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}
fun Activity.checkNetworkConnectivity(): Boolean {

    if (isNetworkAvailable())
        return true
    else {
        runOnUiThread {

            val parentLayout: View = window.decorView.findViewById(android.R.id.content)

            val snackBar: Snackbar = Snackbar.make(parentLayout, getString(R.string.network_connection_issue), Snackbar.LENGTH_SHORT).setAction(getString(R.string.ok), View.OnClickListener { }).setDuration(2);

            snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.color_bt))
            snackBar.setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            val textView: TextView = snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            val typeface: Typeface? = ResourcesCompat.getFont(this, R.font.robo_medium)
            textView.typeface = typeface;

            snackBar.show()

        }
        return false
    }
}


fun Activity.showSnackBar(msg:String ){

    runOnUiThread {

        val parentLayout: View = window.decorView.findViewById(android.R.id.content)

        val snackBar: Snackbar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_SHORT).setAction(getString(R.string.ok), View.OnClickListener { }).setDuration(2000);

        snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.snack_bg))
        snackBar.setActionTextColor(ContextCompat.getColor(this, R.color.snack_text_color))

        val textView: TextView = snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, R.color.snack_text_color))


        snackBar.show()

    }



}