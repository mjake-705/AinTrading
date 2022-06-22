package com.ain.trading.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Children
import com.ain.trading.api.Item
import com.ain.trading.api.QuantityResponse
import com.ain.trading.customviews.MyApplication
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_items.view.*
import java.util.ArrayList
import com.google.android.material.bottomsheet.BottomSheetDialog
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.LinearLayout


class CartAdapter(var mFeedsList: MutableList<Item>, val mListener: OnchildCallBack) : RecyclerView.Adapter<CartAdapter.FeedsVH>() {
    var mChildrenList: MutableList<Children> = ArrayList<Children>()
    var mquntyList: MutableList<QuantityResponse> = ArrayList<QuantityResponse>()
    var quantityList: MutableList<String> =  ArrayList<String>()
    var context: Context?=null
    /*private var bottomSheetDialosg: BottomSheetDialog? = null
    var bottomSheetDialog: BottomSheetDialog? = null
     var bottomSheetBehavior: BottomSheetBehavior?= null
     var bottomSheetView: View? = null*/
    //var qty = null;


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cart_items, parent, false)
        context = parent.getContext();
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)
       // holder.number_picker?.isEnabled=false

        if(!mFeedsList[position].image_path.isNullOrEmpty()) {

            Picasso.with(MyApplication.instance).load(mFeedsList[position].image_path).fit()
            .placeholder(R.drawable.bg_dummy)
            .error(R.drawable.bg_dummy)
            .into(holder?.mallFeedIV)}
       /* holder?.number_picker?.setActionEnabled(ActionEnum.MANUAL,false)
        holder?.number_picker?.setActionEnabled(ActionEnum.DECREMENT,false)
        holder?.number_picker?.setActionEnabled(ActionEnum.INCREMENT,false)
        holder?.number_picker?.min=1*/
        holder?.title_id?.text = mFeedsList[position].product_name
    //    holder?.qty_id?.text =   context?.getString(R.string.qty)+mFeedsList[position].cart_quantity.toString()
        holder?.solid_id?.text = mFeedsList[position].product_id.toString()
        holder?.sar_id?.text = mFeedsList[position].cart_amount+" SAR"
        holder?.sr_id?.text = mFeedsList[position].cart_amount+" SAR"
        holder?.quantity_value?.text = mFeedsList[position].cart_quantity.toString()

        var qty = mFeedsList[position].quantity
        for (i in 1..qty) {
            quantityList.add(i.toString())
        }
        holder?.quantity_click?.setOnClickListener {
            bottomSheetDialogFun(quantityList,holder,position)

        }


        holder?.itemView?.setOnClickListener {
            mListener?.onItemClickCallback(position,mFeedsList[position].product_id.toString(),mFeedsList[position].category.category_id.toString())
        }
        holder.itemView.move_to.setOnClickListener {
            mListener.onMoveToWishlist(position,mFeedsList[position].product_id.toString())
        }

        holder.itemView.cros_img_id.setOnClickListener {
            mListener.onServiceClickCallBack(position,mFeedsList[position].cart_id.toString())
        }


        holder.itemView.arw_qty_id.setOnClickListener {
            mListener.onServiceClickQuantityCallBack(position,mFeedsList[position].product_id.toString())
        }


    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }



    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var mallFeedIV = itemView?.findViewById<ImageView>(R.id.sht_img_id)
        var cros_img_id = itemView?.findViewById<LinearLayout>(R.id.cros_img_id)

        var title_id = itemView?.findViewById<TextView>(R.id.title_id)
        var solid_id = itemView?.findViewById<TextView>(R.id.solid_id)
      //  var qty_id = itemView?.findViewById<TextView>(R.id.qty_id)
        var sar_id = itemView?.findViewById<TextView>(R.id.sar_id)
        var sr_id = itemView?.findViewById<TextView>(R.id.sr_id)
     //   var qty_spinner = itemView?.findViewById<Spinner>(R.id.qty_spinner)
       // var edit_cart = itemView?.findViewById<TextView>(R.id.edit_cart)
        var move_to = itemView?.findViewById<TextView>(R.id.move_to)
        //var number_picker = itemView?.findViewById<NumberPicker>(R.id.number_picker)
    //    var qty_spinner = itemView?.findViewById<MaterialSpinner>(R.id.qty_spinner)
        var quantity_click = itemView?.findViewById<ConstraintLayout>(R.id.quantity_click)
        var quantity_value = itemView?.findViewById<AppCompatTextView>(R.id.quantity_value)

    }
    interface OnchildCallBack{
        fun onServiceClickQuantityCallBack(position: Int, product_id: String)
        fun onServiceClickCallBack(position: Int, cart_id: String)
        fun onUpdate(position: Int, edit_count: String, productId: Int)
        fun onMoveToWishlist(position: Int, productId: String)
       fun onItemClickCallback(position: Int,product_id: String,category_id:String)


    }

    private fun bottomSheetDialogFun(
        plantsList: MutableList<String>,
        holder: FeedsVH,
        position: Int
    )
    {
       val bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet, null)
        val bottomSheetDialog = BottomSheetDialog(this.context!!)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()

        val qty_selected = bottomSheetDialog.findViewById<AppCompatTextView>(R.id.qty_selected)
        val bottomRecyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.bottomRecyclerView)
        val dateParams = bottomRecyclerView?.getLayoutParams() as LinearLayout.LayoutParams
        bottomRecyclerView.setLayoutParams(dateParams)
        val locationChangeAdapter =
            QuantityCountAdapter(this.context, plantsList, bottomSheetDialog)

        val manager = LinearLayoutManager(
            this.context,
            RecyclerView.HORIZONTAL,
            false
        )

        bottomRecyclerView.setLayoutManager(manager)
        bottomRecyclerView.setAdapter(locationChangeAdapter)

        qty_selected?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val quantityValue = locationChangeAdapter.getQuantity()
                holder.quantity_value?.setText(quantityValue)
                mListener.onUpdate(position, quantityValue.toString(), mFeedsList[position].cart_id)

                bottomSheetDialog.dismiss()
            }
        })

    }

}