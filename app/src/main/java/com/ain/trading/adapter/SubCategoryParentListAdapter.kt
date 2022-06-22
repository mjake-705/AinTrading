package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.SubCatParent
import com.ain.trading.api.SubparentCategoryData
import com.ain.trading.customviews.MyApplication
import com.squareup.picasso.Picasso
import java.util.ArrayList

class SubCategoryParentListAdapter (var mFeedsList: MutableList<SubparentCategoryData>, val mListener: OnchildCallBack) : RecyclerView.Adapter<SubCategoryParentListAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.sub_parent_item_list, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)

        if(mFeedsList[position].image_path.isNotEmpty()) {
            Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
                .placeholder(R.drawable.bg_dummy)
                .error(R.drawable.bg_dummy)
                .into(holder?.Parent_categry_TLFeedsIV)
        }

        holder?.cat_name_Tv?.text = mFeedsList[position].category_title

        // holder?.bg_Cl?.setBackgroundColor(ContextCompat.getColor(MyApplication.instance!!, R.color.color_bt))
        //holder?.bg_Cl?.setBackgroundResource(R.drawable.feed_oval)
        holder?.itemView.setOnClickListener {
            mListener.onChildClick(position,mFeedsList[position].category_title,mFeedsList[position].category_id.toString())
        }


    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

          var Parent_categry_TLFeedsIV = itemView?.findViewById<ImageView>(R.id.Parent_categry_TLFeedsIV)
        // var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
        var cat_name_Tv = itemView?.findViewById<TextView>(R.id.subcategoryListNames)


    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            category_title:String,
            category_id:String
        )
        fun onServiceClickCallBack(position: Int)


    }
}