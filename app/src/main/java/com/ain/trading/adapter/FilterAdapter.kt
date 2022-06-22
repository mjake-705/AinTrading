package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.FilterData

import com.ain.trading.api.FilterResponse
import com.ain.trading.customviews.MyApplication
import com.squareup.picasso.Picasso

class FilterAdapter (var mFeedsList: MutableList<FilterData>, val mListener: OnchildCallBack) : RecyclerView.Adapter<FilterAdapter.FeedsVH>() {
    //var filterModels:ArrayList<FilterData>?=null
    var filterModels: MutableList<FilterData> =mFeedsList
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.filter_items, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)
/*
 Picasso.with(MyApplication.instance).load(mFeedsList[position].icon).fit()
             .placeholder(R.drawable.bg_dummy)
             .error(R.drawable.bg_dummy)
             .into(holder?.mallFeedIV)*/


        holder?.textViewName?.text = mFeedsList[position]?.filter_title

        // holder?.bg_Cl?.setBackgroundColor(ContextCompat.getColor(MyApplication.instance!!, R.color.color_bt))
        //holder?.bg_Cl?.setBackgroundResource(R.drawable.feed_oval)


        holder?.itemView.setOnClickListener {
            if(mFeedsList[position]?.filter_id!=null)
                mListener.onChildClick(position,mFeedsList[position]?.filter_id.toString())
        }

    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }

    /*fun setItemSelected(position: Int) {
      *//* for (filterModel in mFeedsList)
        {
          filterModel.childs.get(position).isSelected=false

        }*//*
        if (position != -1) {
           filterModels.get(position).childs.get(position)?.isSelected=true
           // mFeedsList.chi
            notifyDataSetChanged()
        }
    }*/


    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        //  var mallFeedIV = itemView?.findViewById<ImageView>(R.id.sht_img_id)
        // var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
        var textViewName = itemView?.findViewById<TextView>(R.id.txt_item_list_title)


    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            filter_id: String

        )
        fun onServiceClickCallBack(position: Int)


    }


}
