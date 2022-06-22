package com.ain.trading.ui.fragments.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.ain.trading.R
import com.ain.trading.api.LanguageUpdate
import com.ain.trading.api.ProfileDetails
import com.ain.trading.ui.activity.MyAddress.MyAddressActivity
import com.ain.trading.ui.activity.Notifications.NotificationActivity
import com.ain.trading.ui.activity.changepassword.ChangePasswordActivity
import com.ain.trading.ui.activity.login.AccountActivity
import com.ain.trading.ui.activity.orders.MyOrdersActivity
import com.ain.trading.ui.activity.splash.Splash_Activity
import com.ain.trading.utils.*
import kotlinx.android.synthetic.main.profile_frag.*



class FragmentProfile: Fragment(),ProfileView {
    override fun OnProfileLanguageUpdateSuccess(body: LanguageUpdate?) {
        startActivity(
            Intent(
                activity,
                Splash_Activity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        activity?.finish()
    }

    override fun OnProfileUpdateSuccess(body: ProfileDetails?) {
        activity?.showSnackBar("updated Succcessfully")
        disableField()
        profilePresenter.getProfile()
    }

    override fun OnProfileDetailFetched(body: ProfileDetails?) {
        val myString=body?.data?.firstname
        val upperString = myString?.substring(0, 1)?.toUpperCase() + myString?.substring(1)
        username_Tv.text=upperString

        firsname_Et.setText(body?.data?.firstname.toString())
        lastnameEt.setText(body?.data?.lastname.toString())
        mobEt.setText(body?.data?.number.toString())
        emailEt.setText(body?.data?.email.toString())
        genderEt.setText(body?.data?.gender.toString())
    }
    protected fun hideKeyboard(view: View) {
        val `in` = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
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
    private var checkedItem = 0
    var gender: Array<String>? = null
    lateinit var logOutDialog: AinAlertDialog
    private lateinit var profilePresenter: ProfilePresenter
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.profile_frag, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingDialog = LoadingDialog(activity = activity)
        profilePresenter = ProfilePresenter(this)
        disableField()
        profile_top_cl.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(view:View, ev: MotionEvent):Boolean {
                hideKeyboard(view)
                return false
            }
        })
        profilePresenter.getProfile()
        genderEt.setOnClickListener {
            displaySingleSelectionDialog()
        }
        third_layercl.setOnClickListener {
            displaySingleSelectionDialogforlanguage()
        }
        log_out_Tv.setOnClickListener {
            onlogoutClicked()
        }
        editTV.setOnClickListener {
            if (editTV.text.toString()== resources.getString(R.string.edit)) {
                firsname_Et.isEnabled = true
                lastnameEt.isEnabled = true
                genderEt.isEnabled = true
                editTV.text = resources.getString(R.string.save)
            }
            else if (editTV.text == resources.getString(R.string.save)) {
                if (activity?.checkNetworkConnectivity()!!) {

                    when {
                        firsname_Et.getText().toString().isEmpty() -> {
                            firsname_Et.error = getString(R.string.your_first)
                            firsname_Et.requestFocus()
                        }
                        lastnameEt.getText().toString().isEmpty() -> {
                            lastnameEt.error = getString(R.string.your_last)
                            lastnameEt.requestFocus()
                        }

                        genderEt.getText().toString().isEmpty() -> {
                            genderEt.error = getString(R.string.gender)
                            genderEt.requestFocus()
                        }
                        else -> {


                            profilePresenter.updateProfile(firsname_Et.text.toString(),
                                lastnameEt.text.toString(),
                                genderEt.text.toString()
                            )

                        }


                    }
                }
            }
        }
        orders_layercl.setOnClickListener {
            startActivity(Intent(activity, MyOrdersActivity::class.java))
        }
        first_layercl.setOnClickListener {
    startActivity(Intent(activity, NotificationActivity::class.java))
}
        secnd_layercl.setOnClickListener {
            startActivity(Intent(activity, ChangePasswordActivity::class.java))
        }


        address_layercl.setOnClickListener {
            startActivity(Intent(activity, MyAddressActivity::class.java))
        }
    }

    private fun onlogoutClicked() {
        logOutDialog = AinAlertDialog(activity!!)

        logOutDialog.show(getString(R.string.logout), getString(R.string.sure_logout), getString(R.string.yes),getString(R.string.no),
            positiveButtonListener = View.OnClickListener {
                logOutDialog.dismiss()
                val intent = Intent(activity, AccountActivity::class.java)
                AinPref.setCustomerId("")
                AinPref.setAuthToken("")
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            },negativeButtonListener = View.OnClickListener {
                logOutDialog.dismiss()
            })
    }

    private fun displaySingleSelectionDialogforlanguage() {
        var androidVersions = resources.getStringArray(R.array.android_language)
        val dialogBuilder = AlertDialog.Builder(activity!!)
        dialogBuilder.setTitle("Select Language?")
        dialogBuilder.setSingleChoiceItems(androidVersions, checkedItem,
            { dialogInterface, which -> checkedItem = which })
        dialogBuilder.setPositiveButton("Done"){dialog,which->

            if (activity?.isNetworkAvailable()!!) {
                if ((androidVersions as Array<String>)[this?.checkedItem].equals("English")) {
                 AinPref.setOnlanguageIsSet(true)
                 AinPref.setLanguageId("1")
                    profilePresenter.updateLanguage(AinPref.getLanguageId())

                } else if ((androidVersions as Array<String>)[checkedItem].equals("الإنجليزية")) {
               AinPref.setOnlanguageIsSet(true)
              AinPref.setLanguageId("1")
                    profilePresenter.updateLanguage(AinPref.getLanguageId())

                } else if ((androidVersions as Array<String>)[checkedItem].equals("عربى")) {
                    AinPref.setOnlanguageIsSet(true)
                    AinPref.setLanguageId("2")
                    profilePresenter.updateLanguage(AinPref.getLanguageId())
                } else if ((androidVersions as Array<String>)[checkedItem].equals("Arabic")) {
          AinPref.setOnlanguageIsSet(true)
          AinPref.setLanguageId("2")
                    profilePresenter.updateLanguage(AinPref.getLanguageId())
                } else {
                    dialog.dismiss()
                }


// activity?.showSnackBar((androidVersions as Array<String>)[checkedItem])

            } else {
//startActivity(
// Intent(activity, NoNetworkActivity::class.java))
            }

        }
        dialogBuilder.create().show()


    }


    private fun disableField() {
        editTV.text = resources.getString(R.string.edit)
        username_Tv.isEnabled = false
        firsname_Et.isEnabled = false
        lastnameEt.isEnabled = false
        mobEt.isEnabled = false
        emailEt.isEnabled = false
        genderEt.isEnabled = false
    }

    private fun displaySingleSelectionDialog() {
        gender = resources.getStringArray(R.array.gender)
        val dialogBuilder = AlertDialog.Builder(activity!!)
        dialogBuilder.setTitle(getString(R.string.select_lang))
        dialogBuilder.setSingleChoiceItems(gender, checkedItem,
            { dialogInterface, which -> checkedItem = which })
        dialogBuilder.setPositiveButton(getString(R.string.done)) { dialog, which ->

            if (activity?.checkNetworkConnectivity()!!) {
                if ((gender as Array<String>)[checkedItem].equals("male")) {
                    genderEt.setText("Male")

                } else if ((gender as Array<String>)[checkedItem].equals("الذكر")) {
                    genderEt.setText("الذكر")
                } else if ((gender as Array<String>)[checkedItem].equals("female")) {
                    genderEt.setText("Female")
                } else if ((gender as Array<String>)[checkedItem].equals("أنثى")) {
                    genderEt.setText("أنثى")
                } else {
                    dialog.dismiss()
                }


                //  activity?.showSnackBar((androidVersions as Array<String>)[checkedItem])

            }
            else{
                /*   startActivity(
                       Intent(activity, NoNetworkActivity::class.java)
                   )*/
            }

        }
        dialogBuilder.create().show()
    }
}
