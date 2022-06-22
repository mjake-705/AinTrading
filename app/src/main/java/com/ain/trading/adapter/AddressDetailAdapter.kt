package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.Item
import com.ain.trading.customviews.MyApplication
import com.ain.trading.customviews.ServiceObject
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AddressDetailAdapter(var mFeedsList: MutableList<Item>, val mListener: AddressDetailAdapter.OnchildCallBack) : RecyclerView.Adapter<AddressDetailAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.address_details_items, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)

        if(!mFeedsList[position].image_path.isNullOrEmpty()) {

            Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
                .placeholder(R.drawable.bg_dummy)
                .error(R.drawable.bg_dummy)
                .into(holder?.mallFeedIV)}

         holder?.rateTV?.text = mFeedsList[position].price
        holder?.estimatedTv?.text = mFeedsList[position].product_name
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

        var mallFeedIV = itemView?.findViewById<ImageView>(R.id.addresscartIV)
        // var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
        var rateTV = itemView?.findViewById<TextView>(R.id.rateTV)
        var estimatedTv= itemView?.findViewById<TextView>(R.id.estimatedTv)


    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            mChildrenList: MutableList<Children>
        )
        fun onEmptyClick(position: Int)
    }
}