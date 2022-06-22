package com.ain.trading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ain.trading.R
import com.ain.trading.api.Child
import com.ain.trading.api.FilterData
import com.ain.trading.api.FilterResponse
import com.ain.trading.ui.activity.filter.FilterPresenter


/*

class FilterItemsAdapter (var mFeedsList: MutableList<Child>, val mListener: OnchildCallBack) : RecyclerView.Adapter<FilterItemsAdapter.FeedsVH>() {
    private val array = SparseBooleanArray()
    lateinit var mItemClickListener: OnItemClickListener
    var filterModels: MutableList<Child> =mFeedsList
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FeedsVH {
        val view = LayoutInflater.from(parent?.context).inflate(com.ain.trading.R.layout.list_items_filter, parent, false)
        return FeedsVH(view)
    }

    override fun onBindViewHolder(holder: FeedsVH, position: Int) {
        //  holder?.mallFeedIV?.setImageResource(R.drawable.home_icon)

*/
/* Picasso.with(MyApplication.instance).load(mFeedsList[position].icon).fit()
             .placeholder(R.drawable.bg_dummy)
             .error(R.drawable.bg_dummy)
             .into(holder?.mallFeedIV)*//*

       */
/* if (array.get(position))
        {
            holder?.cbSelected?.setChecked(true)
        }
        else
        {
            holder?.cbSelected?.setChecked(false)
        }*//*

      holder?.cbSelected?.setChecked(mFeedsList[position]?.isSelected)
        holder?.textViewName?.text = (mFeedsList[position]?.filter_title)

        // holder?.bg_Cl?.setBackgroundColor(ContextCompat.getColor(MyApplication.instance!!, R.color.color_bt))
        //holder?.bg_Cl?.setBackgroundResource(R.drawable.feed_oval)


        holder?.itemView.setOnClickListener {

            if(mFeedsList[position]?.filter_id!=null)
                array.put(position, true)
            notifyDataSetChanged()
                mListener.onChildClick(position,mFeedsList[position]?.filter_title.toString())
        }

    }

    override fun getItemCount(): Int {
        return mFeedsList.size
    }

    fun setItemSelected(position: Int) {
        */
/* for (filterModel in mFeedsList)
          {
            filterModel.childs.get(position).isSelected=false

          }*//*

        if (position != -1) {
            filterModels.get(position)?.isSelected=true
            // mFeedsList.chi
            notifyDataSetChanged()
        }
    }

    interface OnItemClickListener {

        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }


    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        //  var mallFeedIV = itemView?.findViewById<ImageView>(R.id.sht_img_id)
        // var bg_Cl = itemView?.findViewById<ConstraintLayout>(R.id.bg_Cl)
        var textViewName = itemView?.findViewById<TextView>(R.id.txt_item_list_title)
        var cbSelected = itemView?.findViewById<CheckBox>(R.id.cbSelected)


    }
    interface OnchildCallBack{
        fun onChildClick(
            position: Int,
            filter_id: String

        )
        fun onServiceClickCallBack(position: Int)


    }

    fun onClick(v: View) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v, itemCount)
        }
    }
}
*/



class FilterItemsAdapter(
    var mFeedsList: MutableList<Child>,
    val mListener: OnchildCallBack,
    var filterPresenter: FilterPresenter
) : RecyclerView.Adapter<FilterItemsAdapter.FeedsVH>() {


    var checkedElements: Array<Boolean>

    var filteredElements: List<Child?>? = null

    init {
        filteredElements = mFeedsList
        checkedElements = object : ArrayList<Boolean>() {
            init {
                for (i in 0 until (filteredElements?.size ?: 0)) {
                    add(filteredElements?.get(i)?.isSelected ?: false)
                }
            }
        }.toTypedArray()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemsAdapter.FeedsVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_items_filter, parent, false)
        return FeedsVH(view)
    }

    override fun getItemCount(): Int {
        return filteredElements?.size ?: 0
    }

    override fun onBindViewHolder(holder: FilterItemsAdapter.FeedsVH, position: Int) {

        holder.cbSelected?.setChecked(checkedElements[position])

        holder.textViewName?.text = (filteredElements?.get(position)?.filter_title)
        holder.cbSelected?.setOnCheckedChangeListener { _, p1 ->
            try {
                val item = ArrayList<FilterData?>()
                filterPresenter.bodyValue?.data?.forEach {
                    for (i in 0 until it.childs.size) {
                        if(it.childs[i] == filteredElements?.get(holder.adapterPosition)){
                            item.add(it)
                        }
                    }
                }
                if(item.isNotEmpty())
                    item[0]?.childs?.get(holder.adapterPosition)?.isSelected = p1
            } catch (e: Exception) {
                e.printStackTrace()
            }

            checkedElements[position] = p1
        }
    }

    interface OnchildCallBack {
        fun onChildClick(position: Int, filter_id: String)

        fun onServiceClickCallBack(position: Int)

    }

    inner class FeedsVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var textViewName = itemView?.findViewById<TextView>(R.id.txt_item_list_title)

        var cbSelected = itemView?.findViewById<CheckBox>(R.id.cbSelected)

    }
}