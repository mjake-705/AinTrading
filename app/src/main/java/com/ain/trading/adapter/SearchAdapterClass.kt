package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.SearchData
import com.ain.trading.customviews.MyApplication
import com.ain.trading.customviews.ServiceObject
import com.squareup.picasso.Picasso

class SearchAdapterClass(var mFeedsList:List<SearchData>, val mListener: OnchildCallBack) : RecyclerView.Adapter<SearchAdapterClass.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.search_items, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)
        if(!mFeedsList[position].image_path.isNullOrEmpty()) {
         Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
              .placeholder(R.drawable.bg_dummy)
              .error(R.drawable.bg_dummy)
              .into(holder?.searchImage)}


        holder?.searchTextView?.text = mFeedsList[position].product_name
          holder?.searchPrice?.text = mFeedsList[position].price

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



        holder?.itemView?.setOnClickListener {
            mListener.onServiceClickCallBack(position,mFeedsList[position].product_token,mFeedsList[position].product_id.toString(),mFeedsList[position].category.category_id.toString())
        }

    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var searchImage = itemView?.findViewById<ImageView>(R.id.search_imageid)

          var searchTextView= itemView?.findViewById<TextView>(R.id.searchTv)
        var searchPrice= itemView?.findViewById<TextView>(R.id.searchPriceTv)


    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            mChildrenList: MutableList<Children>
        )
        fun onServiceClickCallBack(
            position: Int,
            product_token: String,
            productId: String,
            categoryId: String
        )
    }
}