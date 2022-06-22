package com.ain.trading.ui.activity.filter

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.FilterItemsAdapter
import com.ain.trading.utils.CustomListCountryDialog
import com.ain.trading.utils.LoadingDialog
import kotlin.collections.ArrayList
import android.os.Parcelable
import com.ain.trading.adapter.FilterAdapter
import com.ain.trading.api.*
import kotlinx.android.synthetic.main.activity_filter.*


class FilterActivity : AppCompatActivity(), FilterView {
    override fun onFilterProductsFetched(body: ParentProductList?) {

    }

    override fun onFilterItemsFetched(body: List<Child>) {
        pickfilter.clear()
        if (body != null) {
            for (i in body.indices)
                pickfilter.add(body[i])
            // pickfilter.addAll(body.get(i).childs.get(i))
            println("pickfilter = ${pickfilter}")
            filteritemAdapter?.notifyDataSetChanged()
            initFilterItemsVp()
        }

        /* if (body?.StatusCode==6000) {
             var dateLength = (body.data?.size)

             for (item in body.data)
             pickfilter.addAll(item.childs.get(0))
             println("pickfilter = ${pickfilter}")
             filteritemAdapter?.notifyDataSetChanged()
             initFilterItemsVp()
           //  showFilterDialog()
         }
         else if(body?.StatusCode==6001){
             Toast.makeText(this,body.message, Toast.LENGTH_SHORT).show()
         }*/
    }

    override fun onFilterFetched(body: FilterResponse?) {
        if (body?.StatusCode == 6000) {
            mfilterList.addAll(body.data)
            rootFilters.addAll(body.data)
            filterAdapter?.notifyDataSetChanged()
            //  no_filters_Tv.visibility= View.GONE
            // myfilterRL.visibility= View.VISIBLE
        } else if (body?.StatusCode == 6001) {
            // myfilterRL.visibility= View.GONE
            // no_filters_Tv.visibility= View.VISIBLE
        }

    }

    override fun onError(error: String?) {

    }

    override fun showProgress() {
        loadingDialog.show()
    }

    override fun hideProgress() {
        loadingDialog.cancel()
    }

    private val LIST_STATE = "listState"
    private val mListState: Parcelable? = null

    private var rootFilters: MutableList<FilterData> = ArrayList<FilterData>()
    var mfilterList: MutableList<FilterData> = ArrayList<FilterData>()
    var mfilterLists: MutableList<FilterData> = ArrayList<FilterData>()
    var filterAdapter: FilterAdapter? = null //main filter adapter
    var filteritemAdapter: FilterItemsAdapter? = null //item filter adapter
    private var selectedFilter: Child? = null
    private lateinit var pickFilter: CustomListCountryDialog<Child>

    private var pickfilter: MutableList<Child> = ArrayList<Child>()
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var filterPresenter: FilterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        filterPresenter = FilterPresenter(this)
        loadingDialog = LoadingDialog(activity = this)
        pickFilter = CustomListCountryDialog(this)
        filterPresenter.getFilter() //main filter

        initFilterVp()

        /* value_filters_Tv?.setOnClickListener {

         }*/

    }

    private fun initFilterItemsVp() {
        filteritemAdapter =
            FilterItemsAdapter(pickfilter, object : FilterItemsAdapter.OnchildCallBack {

                override fun onChildClick(position: Int, filter_id: String) {

                }

                override fun onServiceClickCallBack(position: Int) {
                }


            }, filterPresenter)
        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        filter_value_listview.layoutManager = horizontalLayoutManager
        filter_value_listview.adapter = filteritemAdapter

        btn_filter.setOnClickListener {
            val selectedChildren = ArrayList<Child>()
            filterPresenter.bodyValue?.data?.forEach {
                selectedChildren.addAll(it.childs.filter {
                    it.isSelected
                })
            }

            val any = ArrayList<Int>()
            selectedChildren.forEach {
                any.add(it.filter_id)
            }
            //    println(GsonBuilder().setPrettyPrinting().create().toJson(any.joinToString()))
            //      startActivityForResult(Intent(this@FilterActivity, ProductListActivity::class.java).putExtra("filter_id",any.joinToString()),100)
            val intent = intent
            intent.putExtra("filter_id", any.joinToString())
            setResult(100, intent)
            finish()
        }
    }

    private fun showFilterDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        dialog.setCancelable(true)
        pickFilter.show(pickfilter,
            object : CustomListCountryDialog.OnListItemClickListener<Child> {
                override fun onListItemClicked(item: Child) {
                    selectedFilter = item
                    //      value_filters_Tv.setText(selectedFilter?.filter_title)
                    //      addressBookPresenter.getSubRegion(state_id.toString())
                }
            })
    }

    private fun initFilterVp() {
        filterAdapter = FilterAdapter(mfilterList, object : FilterAdapter.OnchildCallBack {
            override fun onChildClick(position: Int, filter_id: String) {

                filterPresenter.getfilterItems(filter_id)  //filter items
                filterItemListClicked(position)
                //filterAdapter?.setItemSelected(position)
            }

            override fun onServiceClickCallBack(position: Int) {
            }


        })
        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        filter_dialog_listview.layoutManager = horizontalLayoutManager
        filter_dialog_listview.adapter = filterAdapter

    }

    private fun filterItemListClicked(position: Int) {
        //  filteritemAdapter?.setItemSelected(position)
    }


    /*  filterAdapter?.setOnItemClickListener { v, position ->
          filterItemListClicked(position, v)
          adapter!!.setItemSelected(position)
      }*/

}
