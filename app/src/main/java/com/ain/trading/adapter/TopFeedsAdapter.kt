package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Data
import com.ain.trading.api.ParentSubCategory

import com.ain.trading.customviews.MyApplication
import com.squareup.picasso.Picasso

class TopFeedsAdapter (var mFeedsList: List<Data>, val mListener: OnchildCallBack) : RecyclerView.Adapter<TopFeedsAdapter.FeedsVH>() {
    var mDataList: MutableList<ParentSubCategory> = ArrayList<ParentSubCategory>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.category_feed_single_item, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        /*  Picasso.with(MyApplication.instance).load(mFeedsList[position].icon).fit()
            .placeholder(R.drawable.bg_dummy)
            .error(R.drawable.bg_dummy)
            .into(holder?.mallFeedIV)
        holder?.cat_name_Tv?.text = mFeedsList[position].offerprice
                holder?.bg_Cl?.setBackgroundResource(R.drawable.feed_oval)
    */


        if (!mFeedsList[position].image.isNullOrEmpty()) {
            Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
                .placeholder(R.drawable.bg_dummy)
                .error(R.drawable.bg_dummy)
                .into(holder?.mallFeedIV)
        }
        holder?.cat_name_Tv?.text = mFeedsList[position].category_title

        // holder?.bg_Cl?.setBackgroundColor(ContextCompat.getColor(MyApplication.instance!!, R.color.color_bt))
        holder?.bg_Cl?.setBackgroundResource(R.drawable.feed_oval)
     /*   holder?.itemView.setOnClickListener {
            mListener.onChildClick(position)
        }*/
        holder?.itemView.setOnClickListener {
            mListener.onChildClick(position,mFeedsList[position]?.category_id.toString(),mFeedsList[position]?.category_title)
        }
    }



    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var mallFeedIV = itemView?.findViewById<ImageView>(R.id.mallFeedsIV)
        var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
        var cat_name_Tv = itemView?.findViewById<TextView>(R.id.cat_name_Tv)


    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            category_id: String,
            categoryTitle: String
        )
        fun onEmptyClick(position: Int)
    }
}