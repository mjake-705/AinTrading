package com.ain.trading.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.BaseItem
import com.ain.trading.api.States

class LoadingDialog(private val activity: Activity?) {

    var mLoadingDialog: Dialog? = null

    init {

        mLoadingDialog = Dialog(activity!!)
        mLoadingDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mLoadingDialog?.window!!.setBackgroundDrawable(ColorDrawable(0))
        mLoadingDialog?.setContentView(R.layout.custom_loading_dialog)
        mLoadingDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mLoadingDialog?.setCanceledOnTouchOutside(false)
        mLoadingDialog?.setCancelable(true)

    }

    fun show() {

        if (!mLoadingDialog?.isShowing!!) {
            mLoadingDialog?.show()
        }

    }

    fun cancel() {

        if (mLoadingDialog != null && mLoadingDialog?.isShowing!!) {
            mLoadingDialog?.cancel()
            mLoadingDialog?.dismiss()
        }

    }


}

class AinAlertDialog(activity: Activity) {

    private var dialog: Dialog = Dialog(activity)

    var onClickListener = View.OnClickListener {
        dismiss()
    }

    var titleTV: TextView
    var messageTV: TextView
    var positiveButtonTV: TextView
    var negativeButtonTV: TextView


    init {

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        dialog.setContentView(R.layout.alert_dialog)
        dialog.setCanceledOnTouchOutside(true)

        titleTV = dialog.findViewById(R.id.titleTV)
        messageTV = dialog.findViewById(R.id.messageTV)
        positiveButtonTV = dialog.findViewById(R.id.positiveTV)
        negativeButtonTV = dialog.findViewById(R.id.negativeTV)
    }

    fun show(titleText: String, message: String,
             positiveButtonText: String, negativeButtonText: String = "",
             positiveButtonListener: View.OnClickListener = onClickListener,
             negativeButtonListener: View.OnClickListener = onClickListener, isTitleRequired: Boolean = true) {

        titleTV.text = titleText
        messageTV.text = message
        positiveButtonTV.text = positiveButtonText
        negativeButtonTV.text = negativeButtonText

        positiveButtonTV.setOnClickListener(positiveButtonListener)
        negativeButtonTV.setOnClickListener(negativeButtonListener)

        dialog.show()

    }

    fun dismiss() {
        dialog.dismiss()
        dialog.cancel()
    }
}

class AinSignInAlertDialog(activity: Activity) {

    private var dialog: Dialog = Dialog(activity)

    var onClickListener = View.OnClickListener {
        dismiss()
    }

    var titleTV: TextView
    var IV: ImageView
    var messageTV: TextView
    var positiveButtonTV: TextView
    var negativeButtonTV: TextView


    init {

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        dialog.setContentView(R.layout.signin_alert_dialog)
        dialog.setCanceledOnTouchOutside(true)

        titleTV = dialog.findViewById(R.id.titleTV)
        IV = dialog.findViewById(R.id.iv_signin)
        messageTV = dialog.findViewById(R.id.messageTV)
        positiveButtonTV = dialog.findViewById(R.id.positiveTV)
        negativeButtonTV = dialog.findViewById(R.id.negativeTV)
    }

    fun show(titleText: String, titleIv:Drawable, message: String,
             positiveButtonText: String, negativeButtonText: String = "",
             positiveButtonListener: View.OnClickListener = onClickListener,
             negativeButtonListener: View.OnClickListener = onClickListener, isTitleRequired: Boolean = true) {

        titleTV.text = titleText
        IV.setImageDrawable(titleIv)
        messageTV.text = message
        positiveButtonTV.text = positiveButtonText
        negativeButtonTV.text = negativeButtonText

        positiveButtonTV.setOnClickListener(positiveButtonListener)
        negativeButtonTV.setOnClickListener(negativeButtonListener)

        dialog.show()

    }

    fun dismiss() {
        dialog.dismiss()
        dialog.cancel()
    }
}

class CustomListCountryDialog<T>(private var activity: Activity) {

    private var dialog: Dialog = Dialog(activity)

    private lateinit var onListItemClickListener: OnListItemClickListener<T>


    var listRV: RecyclerView


    init {

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(0))
        dialog?.setContentView(R.layout.list_dialog)
        dialog?.setCanceledOnTouchOutside(true)

        listRV = dialog?.findViewById(R.id.listRV)

    }


    fun show(
        items: MutableList<T>, onListItemClickListener: OnListItemClickListener<T>,
        isCancelable: Boolean = true
    ) {

        this.onListItemClickListener = onListItemClickListener


        val listAdapter = ListAdapter(items)
        listRV.layoutManager = LinearLayoutManager(activity)
        listRV.adapter = listAdapter

        dialog?.setCancelable(isCancelable)
        dialog?.setCanceledOnTouchOutside(isCancelable)

        var params: ViewGroup.LayoutParams = listRV.layoutParams

        if (items.size > 10) {
            params.height = 1000
            listRV.layoutParams = params
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            listRV.layoutParams = params
        }
        dialog?.show()

    }

    fun dismiss() {
        dialog?.dismiss()
        dialog?.cancel()
    }

    private inner class ListAdapter(var items: List<T>) :
        RecyclerView.Adapter<ListAdapter.ListVH>() {
        override fun onBindViewHolder(holder: ListVH, position: Int) {
            holder?.popUpTV?.text = (items[position] as BaseItem).getTitle()
            if ((items.size - 1) == (position))
                holder?.lineView?.visibility = View.GONE
            else
                holder?.lineView?.visibility = View.VISIBLE

            holder?.itemView?.setOnClickListener {
                dismiss()
                onListItemClickListener.onListItemClicked(items.get(position))


            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ListVH {
            return ListVH(
                LayoutInflater.from(parent?.context).inflate(
                    R.layout.list_item,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return items.size
        }


        inner class ListVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

            var popUpTV: TextView? = null
            var lineView: View? = null

            init {
                popUpTV = itemView?.findViewById<TextView>(R.id.popUpTV)
                lineView = itemView?.findViewById(R.id.lineView)
            }
        }

    }


    interface OnListItemClickListener<T> {

        fun onListItemClicked(item: T)
    }
}