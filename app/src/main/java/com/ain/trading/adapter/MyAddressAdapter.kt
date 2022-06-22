package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.AddressList
import com.ain.trading.api.Children

import com.ain.trading.customviews.MyApplication
import com.ain.trading.customviews.ServiceObject
import com.squareup.picasso.Picasso
import java.util.ArrayList


class MyAddressAdapter(var mFeedsList:List<AddressList>, val mListener: OnchildCallBack) : RecyclerView.Adapter<MyAddressAdapter.FeedsVH>() {

    private var lastCheckedPosition = -1
    private var onelastCheckedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.my_address_items, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {

        holder?.myaddress_name?.text = mFeedsList[position].address_holder
        holder?.mypermenent_address?.text = mFeedsList[position].primary_address
         holder?.mycity?.text = mFeedsList[position].city_name

        holder?.myaddress_location?.text = mFeedsList[position].location
        holder?.myphone_number?.text = mFeedsList[position].primary_number.toString()
        holder?.mystate?.text = mFeedsList[position].location
        holder?.mypincode?.text = mFeedsList[position].postal_code.toString()

       /* if (mFeedsList[position].default==1)
        {
           onelastCheckedPosition=position
            holder.selectaddress?.setChecked(true)

        }
        else if(mFeedsList[position].default==0){
           holder.selectaddress?.setChecked(false)

          //  holder?.selectaddress?.isChecked
        }*/
           holder.selectaddress?.setChecked(position == lastCheckedPosition)
        holder.selectaddress?.setOnClickListener {
            lastCheckedPosition = position
            notifyItemRangeChanged(0,mFeedsList.size)
      //      notifyDataSetChanged()
            var customer_to_address_id=mFeedsList[position].customer_to_address_id.toString()
            var primary_number=mFeedsList[position].primary_number.toString()
                mListener.onChildClick(position,customer_to_address_id,primary_number)

        }


        holder?.editAddressBaseCL?.setOnClickListener {

            mListener.onServiceClickCallBack(position,  mFeedsList[position].customer_to_address_id.toString())

        }

        holder?.address_delete?.setOnClickListener {

            mListener.onServiceClickCallBackDelete(position,  mFeedsList[position].customer_to_address_id.toString())

        }


    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {


        var selectaddress=itemView?.findViewById<RadioButton>(R.id.selectaddress)
        var myaddress_name=itemView?.findViewById<TextView>(R.id.myaddress_name)
        var  mypermenent_address=itemView?.findViewById<TextView>(R.id.mypermenent_address)
        var myaddress_location=itemView?.findViewById<TextView>(R.id.mylocation)
       var  mycity=itemView?.findViewById<TextView>(R.id.mycity)
        var  mystate=itemView?.findViewById<TextView>(R.id.mystate)
        var  myphone_number=itemView?.findViewById<TextView>(R.id.myphone_number)
        var  mypincode=itemView?.findViewById<TextView>(R.id.mypincode)
        var  editbutton=itemView?.findViewById<TextView>(R.id.address_edit)
        var  editAddressBaseCL=itemView?.findViewById<ConstraintLayout>(R.id.editAddressBaseCL)
        var  address_delete=itemView?.findViewById<ConstraintLayout>(R.id.address_delete)
        var  delete_Tv=itemView?.findViewById<TextView>(R.id.delete_Tv)

    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            customer_to_address_id:String,primary_number:String

        )
        fun onServiceClickCallBack(position: Int,customer_to_address_id:String )
        fun onServiceClickCallBackDelete(position: Int,customer_to_address_id:String )

    }
}