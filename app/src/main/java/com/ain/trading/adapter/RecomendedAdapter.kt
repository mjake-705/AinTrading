package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.Parentproduct
import com.ain.trading.customviews.MyApplication
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recomend_field.view.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.net.URL


class RecomendedAdapter (var mFeedsList: MutableList<Parentproduct>, val mListener: ServiceCallback) : RecyclerView.Adapter<RecomendedAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.recomend_field, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)
        if (!mFeedsList[position].image_path.isNullOrEmpty()) {
           /* val newurl = URL(mFeedsList[position].image_path)
            var mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream())
            holder?.mallFeedIv?.setImageBitmap(mIcon_val)
*/
            Picasso.with(MyApplication.instance)?.load(mFeedsList[position].image_path)?.fit()
           // Picasso.with(MyApplication.instance)?.load("http://cdn.journaldev.com/wp-content/uploads/2017/01/android-constraint-layout-sdk-tool-install.png")?.fit()
                ?.placeholder(com.ain.trading.R.drawable.bg_dummy)
                ?.error(com.ain.trading.R.drawable.bg_dummy)
                ?.into(holder?.mallFeedIv)
        }
        holder?.recom_nameTV?.text = mFeedsList[position].product_name
        holder?.recom_priceTV?.text = mFeedsList[position].price+" SAR"

        if (mFeedsList[position].category!=null) {
            holder?.title_child_tv?.text = mFeedsList[position].category.category_title
            holder?.itemView?.setOnClickListener { mListener.onServiceClickCallBack(position,mFeedsList[position].product_token,mFeedsList[position].product_id.toString(),mFeedsList[position].category.category_id.toString()) }


        }
      /* holder?.itemView.recom_cartTv.setOnClickListener {
           mListener.onAddCart(position,mFeedsList[position].product_id.toString())
        }
*/
        //holder?.recom_saveTV?.text = mFeedsList[position].
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

        var mallFeedIv = itemView?.findViewById<ImageView>(R.id.recomended_IV)
        // var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
        var title_child_tv = itemView?.findViewById<TextView>(R.id.title_child_tv)
        var recom_nameTV = itemView?.findViewById<TextView>(R.id.recom_nameTV)
        var recom_priceTV = itemView?.findViewById<TextView>(R.id.recom_priceTV)
       // var recom_saveTV = itemView?.findViewById<TextView>(R.id.recom_saveTV)
   //     var recom_cartTv = itemView?.findViewById<TextView>(R.id.recom_cartTv)


    }
    interface ServiceCallback{
        fun onServiceClickCallBack(
            position: Int,
            product_token: String,
            productId: String,
            categoryId: String
        )
        fun onAddCart(  position: Int,productId: String)
    }
}