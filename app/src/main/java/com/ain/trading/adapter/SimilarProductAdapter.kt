package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.RelatedProducts
import com.ain.trading.api.RelatesProducts
import com.ain.trading.customviews.MyApplication
import com.ain.trading.customviews.ServiceObject

import com.squareup.picasso.Picasso
import java.util.ArrayList



class SimilarProductAdapter(var mFeedsList: MutableList<RelatesProducts>, val mListener: OnchildCallBack) : RecyclerView.Adapter<SimilarProductAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.similar_product_items, parent, false)
        return FeedsVH(view)
    }




    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)
        if(!mFeedsList[position].image_path.isNullOrEmpty()) {
            Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
                .placeholder(R.drawable.ad)
                .error(R.drawable.ad)
                .into(holder?.mallFeedIV)
        }

        holder?.text_id?.text = mFeedsList[position].product_name
        holder?.special_prize_txt_id?.text = mFeedsList[position].price+" SAR"
        holder?.text_id1?.text = mFeedsList[position].product_name
        holder?.itemView?.setOnClickListener { mListener.onServiceClickCallBack(position,mFeedsList[position].product_token,mFeedsList[position].product_id.toString(),mFeedsList[position].category.category_id.toString()) }

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

    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var mallFeedIV = itemView?.findViewById<ImageView>(R.id.similarProductitem_IV)
        // var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
        var text_id = itemView?.findViewById<TextView>(R.id.text_id)
        var special_prize_txt_id = itemView?.findViewById<TextView>(R.id.special_prize_txt_id)
        var text_id1 = itemView?.findViewById<TextView>(R.id.text_id1)


    }
    interface OnchildCallBack{
        fun onServiceClickCallBack(
            position: Int,
            product_token: String,
            productId: String,
            categoryId: String
        )
        fun onEmptyClick(position: Int)
    }
}