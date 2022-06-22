package com.ain.trading.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.Parentproduct
import com.ain.trading.customviews.MyApplication
import com.ain.trading.customviews.ServiceObject

import com.squareup.picasso.Picasso
import java.util.ArrayList


class ProductListAdapter(var mFeedsList: MutableList<Parentproduct>, val mListener: OnchildCallBack) : RecyclerView.Adapter<ProductListAdapter.FeedsVH>(),
    RatingBar.OnRatingBarChangeListener {
    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        /*Toast.makeText(, "New Rating: " + p0,
            Toast.LENGTH_SHORT).show()*/
    }

    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_page_items, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
          holder?.bg_Cl?.setOnRatingBarChangeListener(this)
        if(!mFeedsList[position].image_path.isNullOrEmpty()) {

            Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
                .placeholder(R.drawable.bg_dummy)
                .error(R.drawable.bg_dummy)
                .into(holder?.listPageIV)
        }


        holder?.itemView.setOnClickListener {
            mListener.onChildClick(position,mFeedsList[position].product_id.toString(),mFeedsList[position].product_token)
        }


      holder?.text_id1?.text = mFeedsList[position].product_name
      holder?.special_prize_txt_id?.text = mFeedsList[position].price+" SAR"

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

        var listPageIV = itemView?.findViewById<ImageView>(R.id.listPage_IV)
        var bg_Cl = itemView?.findViewById<RatingBar>(R.id.ratingBar)
         var text_id1 = itemView?.findViewById<TextView>(R.id.text_id1)
         var special_prize_txt_id = itemView?.findViewById<TextView>(R.id.text_id)


    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            product_id:String,
            product_token:String
        )
        fun onEmptyClick(position: Int)
    }
}