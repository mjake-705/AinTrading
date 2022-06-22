package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.Image
import com.ain.trading.customviews.MyApplication
import com.ain.trading.customviews.ServiceObject
import com.squareup.picasso.Picasso



class BrandFocusAdapter(var mFeedsList: MutableList<Image>, val mListener: OnchildCallBack) : RecyclerView.Adapter<BrandFocusAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.brand_foucs_item, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)
        if(!mFeedsList[position].image_path.isNullOrEmpty()) {
            Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
                .placeholder(R.drawable.bg_dummy)
                .error(R.drawable.bg_dummy)
                .into(holder?.mallFeedIV)
        }

        holder?.itemView.setOnClickListener {
            mListener.onChildClick(position)
        }
        // holder?.cat_name_Tv?.text = mFeedsList[position].offerprice

        // holder?.bg_Cl?.setBackgroundColor(ContextCompat.getColor(MyApplication.instance!!, R.color.color_bt))
        //  holder?.bg_Cl?.setBackgroundResource(R.drawable.feed_oval)
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

        var mallFeedIV = itemView?.findViewById<ImageView>(R.id.brandfocusIV)



    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int
        )
        fun onEmptyClick(position: Int)
    }
}