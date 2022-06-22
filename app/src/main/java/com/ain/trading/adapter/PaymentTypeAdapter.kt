package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.Item
import com.ain.trading.api.PaymentTypeResponseData
import com.ain.trading.customviews.MyApplication
import com.squareup.picasso.Picasso
import java.util.ArrayList

class PaymentTypeAdapter (var mFeedsList: MutableList<PaymentTypeResponseData>, val mListener: PaymentTypeAdapter.OnchildCallBack) : RecyclerView.Adapter<PaymentTypeAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    private var lastCheckedPosition=-1
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.payment_items, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)

        /*if(!mFeedsList[position].image_path.isNullOrEmpty()) {

            Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
                .placeholder(R.drawable.bg_dummy)
                .error(R.drawable.bg_dummy)
                .into(holder?.pay_arw)}*/

        holder?.pay_id?.text = mFeedsList[position].payment_type

        holder.addresstypeIdBox?.setTag(position);

/*

        holder?.addresstypeIdBox?.setOnCheckedChangeListener { buttonView, isChecked ->
           var pmode=mFeedsList[position].payment_mode_id.toString()
           mListener.onChildClick(position,pmode)
        }
*/


        holder.addresstypeIdBox?.setChecked(position == lastCheckedPosition)
        holder.addresstypeIdBox?.setOnClickListener {
           notifyItemRangeChanged(0,mFeedsList.size)
   //        notifyDataSetChanged()
            lastCheckedPosition = position

            var pmode=mFeedsList[position].payment_mode_id.toString()
       //    lastCheckedPosition=0
            mListener.onChildClick(position,pmode)


        }

    //    holder?.estimatedTv?.text = mFeedsList[position].product_name
        // holder?.bg_Cl?.setBackgroundColor(ContextCompat.getColor(MyApplication.instance!!, R.color.color_bt))
        //holder?.bg_Cl?.setBackgroundReestimatedTvsource(R.drawable.feed_oval)
/*        holder?.itemView.setOnClickListener {
            if (!mFeedsList[position].children.isNullOrEmpty()) {

                mChildrenList=(mFeedsList[position].children.toMutableList())
                if(!mChildrenList.isNullOrEmpty())
                    mListener.onChildClick(position,mChildrenList)
            }
            else{
                mListener.onEmptyClick(position)
            }


        }*/

    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        //var pay_arw = itemView?.findViewById<ImageView>(R.id.pay_arw)
         var addresstypeIdBox = itemView?.findViewById<RadioButton>(R.id.addresstypeIdBox)
       var pay_id = itemView?.findViewById<TextView>(R.id.pay_id)



    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            pmode: String
        )
        fun onEmptyClick(position: Int)
    }
}
