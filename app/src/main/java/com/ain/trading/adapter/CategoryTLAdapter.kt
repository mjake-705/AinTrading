package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.Data
import com.ain.trading.customviews.MyApplication
import com.squareup.picasso.Picasso


class CategoryTLAdapter(var mFeedsList: MutableList<Data>, val mListener: OnchildCallBack) : RecyclerView.Adapter<CategoryTLAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.frag_category_tab_items, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)
        if(!mFeedsList[position].image_path.isNullOrEmpty()) {

        Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
            .placeholder(R.drawable.bg_dummy)
            .error(R.drawable.bg_dummy)
            .into(holder?.categry_TLFeedsIV)}

          holder?.catTL_name_Tv?.text = mFeedsList[position].category_title

        holder?.itemView.setOnClickListener {
            mListener.onChildClick(position,mFeedsList[position]?.category_id.toString(),mFeedsList[position]?.category_title)
        }
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



       /* holder?.itemView.setOnClickListener {
            mListener.onServiceClickCallBack(position)


        }*/

    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var categry_TLFeedsIV = itemView?.findViewById<ImageView>(R.id.categry_TLFeedsIV)
        // var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
        var catTL_name_Tv = itemView?.findViewById<TextView>(R.id.catTL_name_Tv)


    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            category_id: String,
            categoryTitle: String
        )
        fun onServiceClickCallBack(position: Int)
    }
}

