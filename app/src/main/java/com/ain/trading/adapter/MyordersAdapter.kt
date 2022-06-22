package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.MyOrders
import com.ain.trading.customviews.MyApplication
import com.ain.trading.customviews.ServiceObject
import com.squareup.picasso.Picasso

class MyordersAdapter  (var mFeedsList: MutableList<MyOrders>, val mListener: OnchildCallBack) : RecyclerView.Adapter<MyordersAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.my_oders_items, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)

 /*       Picasso.with(MyApplication.instance).load(mFeedsList[position].products.get(0).image_path).fit()
            .placeholder(R.drawable.bg_dummy)
            .error(R.drawable.bg_dummy)
            .into(holder?.orders_Iv)*/

           holder?.status_id?.text = mFeedsList[position].status_type
          holder?.off_id?.text = mFeedsList[position].created_at
          holder?.jan?.text = mFeedsList[position].order_id.toString()
          holder?.splurge_id?.text = mFeedsList[position].grand_total

        // holder?.bg_Cl?.setBackgroundColor(ContextCompat.getColor(MyApplication.instance!!, R.color.color_bt))
        //holder?.bg_Cl?.setBackgroundResource(R.drawable.feed_oval)



        holder?.itemView.setOnClickListener {
            mListener.onServiceClickCallBack(position,mFeedsList[position]?.order_id.toString())


        }

    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var status_id = itemView?.findViewById<TextView>(R.id.status_id)
        // var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
           var newcolths = itemView?.findViewById<TextView>(R.id.newcolths)
           var off_id = itemView?.findViewById<TextView>(R.id.off_id)
           var jan = itemView?.findViewById<TextView>(R.id.jan)
           var splurge_id = itemView?.findViewById<TextView>(R.id.splurge_id)


    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            mChildrenList: MutableList<Children>
        )
        fun onServiceClickCallBack(position: Int,order_id:String)
    }
}