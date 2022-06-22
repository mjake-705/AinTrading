package com.ain.trading.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.Orderlist
import com.ain.trading.customviews.MyApplication
import com.ain.trading.customviews.ServiceObject
import com.squareup.picasso.Picasso

class Order_listingAdapter  (var mFeedsList: MutableList<Orderlist>, val mListener: OnchildCallBack) : RecyclerView.Adapter<Order_listingAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.order_listing_items, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)

         Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
              .placeholder(R.drawable.bg_dummy)
              .error(R.drawable.bg_dummy)
              .into(holder?.order_item_Iv)

         holder?.newcolths?.text = mFeedsList[position].product_name
         holder?.price_id?.text = mFeedsList[position].price
         holder?.quan_id_TV?.text = mFeedsList[position].quantity.toString()
         holder?.order_id_TV?.text = mFeedsList[position].order_id.toString()

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



        holder?.itemView.setOnClickListener {
            mListener.onServiceClickCallBack(position)


        }

    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var order_item_Iv = itemView?.findViewById<ImageView>(R.id.order_item_Iv)
         //var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
           var newcolths = itemView?.findViewById<TextView>(R.id.newcolths)
           var price_id = itemView?.findViewById<TextView>(R.id.price_id)
           var order_id_TV = itemView?.findViewById<TextView>(R.id.order_id_TV)
           var quan_id_TV = itemView?.findViewById<TextView>(R.id.quan_id_TV)


    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            mChildrenList: MutableList<Children>
        )
        fun onServiceClickCallBack(position: Int)
    }
}