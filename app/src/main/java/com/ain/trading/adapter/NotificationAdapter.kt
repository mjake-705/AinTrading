package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.customviews.MyApplication
import com.ain.trading.customviews.ServiceObject
import com.squareup.picasso.Picasso

class NotificationAdapter(var mFeedsList: MutableList<ServiceObject>, val mListener: OnchildCallBack) : RecyclerView.Adapter<NotificationAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.notification_items, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)

      /*  Picasso.with(MyApplication.instance).load(mFeedsList[position].icon).fit()
            .placeholder(R.drawable.bg_dummy)
            .error(R.drawable.bg_dummy)
            .into(holder?.categry_TLFeedsIV)*/

        //   holder?.cat_name_Tv?.text = mFeedsList[position].offerprice

        // holder?.bg_Cl?.setBackgroundColor(ContextCompat.getColor(MyApplication.instance!!, R.color.color_bt))
        //holder?.bg_Cl?.setBackgroundResource(R.drawable.feed_oval)
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



        holder?.itemView.setOnClickListener {
            mListener.onServiceClickCallBack(position)


        }

    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

     //   var categry_TLFeedsIV = itemView?.findViewById<ImageView>(R.id.notifiction_img)
        // var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
     //   var catTL_name_Tv = itemView?.findViewById<TextView>(R.id.catTL_name_Tv)


    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            mChildrenList: MutableList<Children>
        )
        fun onServiceClickCallBack(position: Int)
    }
}
