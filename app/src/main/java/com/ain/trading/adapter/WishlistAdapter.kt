package com.ain.trading.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.WishListData
import com.ain.trading.customviews.MyApplication
import com.ain.trading.customviews.ServiceWishListclass
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.wish_list.view.*

class WishlistAdapter (var mFeedsList: MutableList<WishListData>, val mListener: OnchildCallBack) : RecyclerView.Adapter<WishlistAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    var wishListId:String?=null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.wish_list, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)
        if (!mFeedsList[position].image_path.isNullOrEmpty()) {
            Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
                .placeholder(R.drawable.menshirt)
                .error(R.drawable.menshirt)
                .into(holder?.mallFeedIV)
        }
        holder.text_id?.text=mFeedsList[position].price+" SAR"
        holder.text_id1?.text=mFeedsList[position].product_name

        holder?.itemView?.setOnClickListener {
            mListener?.onItemClickCallback(position,mFeedsList[position].product_id.toString(),mFeedsList[position].category.category_id.toString())
        }

        holder?.itemView?.wishlistName.setOnClickListener {
            mListener.onServiceClickCallBackAddCart(position,mFeedsList[position].product_id.toString())


        }

        holder?.itemView?.wishListRemove.setOnClickListener {
            mListener.onServiceClickCallBack(position,mFeedsList[position].wishlist_id.toString())

        }


    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var mallFeedIV = itemView?.findViewById<ImageView>(R.id.Image_id)
        //var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
        var text_id = itemView?.findViewById<TextView>(R.id.text_id)
        var text_id1 = itemView?.findViewById<TextView>(R.id.text_id1)


    }
    interface OnchildCallBack{

        fun onServiceClickCallBack(position: Int, wishListId: String)
        fun onServiceClickCallBackAddCart(position: Int, wishListId: String)
        fun onItemClickCallback(position: Int,product_id: String,category_id:String)

    }
}


