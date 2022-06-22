package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.SubCatParent
import java.util.ArrayList


class SubCategoryListAdapter(var mFeedsList: MutableList<SubCatParent>, val mListener: OnchildCallBack) : RecyclerView.Adapter<SubCategoryListAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.subcategorylist_items, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)

       /* Picasso.with(MyApplication.instance).load(mFeedsList[position].icon).fit()
            .placeholder(R.drawable.bg_dummy)
            .error(R.drawable.bg_dummy)
            .into(holder?.mallFeedIV)
*/
          holder?.cat_name_Tv?.text = mFeedsList[position]?.category_title

        // holder?.bg_Cl?.setBackgroundColor(ContextCompat.getColor(MyApplication.instance!!, R.color.color_bt))
        //holder?.bg_Cl?.setBackgroundResource(R.drawable.feed_oval)


        holder?.itemView.setOnClickListener {
            if(!mFeedsList[position]?.category_token.isNullOrEmpty())
            mListener.onChildClick(position,mFeedsList[position]?.category_id,mFeedsList[position]?.category_title)
        }

    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

      //  var mallFeedIV = itemView?.findViewById<ImageView>(R.id.sht_img_id)
        // var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
         var cat_name_Tv = itemView?.findViewById<TextView>(R.id.subcategoryListNames)


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