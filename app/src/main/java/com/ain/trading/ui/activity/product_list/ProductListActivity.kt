package com.ain.trading.ui.activity.product_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.RatingBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.ProductListAdapter
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.filter.FilterActivity
import com.ain.trading.ui.activity.filter.FilterPresenter
import com.ain.trading.ui.activity.filter.FilterView
import com.ain.trading.ui.activity.home.HomePresenter
import com.ain.trading.ui.activity.home.HomeView
import com.ain.trading.ui.activity.product_detail.ProductDetailActivity
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.app_bar_center_title.*
import kotlinx.android.synthetic.main.list_page_top.*

class ProductListActivity : AppCompatActivity(),HomeView, FilterView {
    override fun onFilterFetched(body: FilterResponse?) {

    }

    override fun onFilterItemsFetched(body: List<Child>) {
    }

    override fun onFilterProductsFetched(body: ParentProductList?) {
        println("onFilterProductsFetched")
        onSubCategoryProductsFetched(body)

    }

    override fun onLatestProductfetched(body: ParentProductList?) {

    }

    override fun onBrandsListFetched(body: BrandDatailList?) {

    }

    override fun onCategoryBannerFetched(body: Banners?) {
    }

    override fun onBrandsFetched(body: BrandsList?) {
    }

    override fun onSubCategoryProductsFetched(body: ParentProductList?) {
        println("onSubCategoryProductsFetched")
        println(GsonBuilder().setPrettyPrinting().create().toJson(body))
        mSubCategoryList?.clear()
        if (body?.StatusCode==6000) {
            mSubCategoryList?.addAll(body.data)
            mlistPageAdapter?.notifyDataSetChanged()
            listpage_RV.visibility=View.VISIBLE
            no_content_Tv.visibility=View.GONE
            homeBottomLayout.visibility=View.VISIBLE
        }
        else if(body?.StatusCode==6001)
        {
            listpage_RV.visibility=View.GONE
            no_content_Tv.visibility=View.VISIBLE
            homeBottomLayout.visibility=View.GONE
        }
    }

    override fun onCategoryFetched(body: Maincategory?) {
    }

    override fun onBannersFetched(body: Banners?) {
    }

    override fun onBrandinFocusfetched(body: Banners?) {
    }

    override fun OnAddCartListSuccess(body: CartAddedResponse) {
    }

    override fun onCategoryDetailFetched(body: SubCategoryParent?) {
    }

    override fun onSubCategoryDetailFetched(body: SubcategoryParentDetail?) {
    }

    override fun onError(error: String?) {
        this.showSnackBar(error.toString())
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }
    var mFilterProductList: MutableList<FilterProductsResponse> = ArrayList<FilterProductsResponse>()

    var mSubCategoryList: MutableList<Parentproduct> = ArrayList<Parentproduct>()
    var category_id: String?=null
    var category_title: String?=null
    var mlistPageAdapter: ProductListAdapter? =null
    private lateinit var homePresenter: HomePresenter
    private lateinit var loadingDialog: LoadingDialog
    var ratingbar: RatingBar? =null
    var filter_Id:String?=null
     var filterPresenter: FilterPresenter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        loadingDialog = LoadingDialog(activity = this)

        homePresenter = HomePresenter(this)
        filterPresenter = FilterPresenter(this)

            if (intent.extras != null && intent.extras!!.containsKey("category_id")) {
                category_id = intent.getStringExtra("category_id")
                category_title = intent.getStringExtra("category_title")
            }

/*var s=   intent.getStringExtra("filter_id")
        println(GsonBuilder().setPrettyPrinting().create().toJson(s))*/

        homePresenter.getProducts(category_id)
            initListPageVP()

        initToolBar()


        filer_id.setOnClickListener {
            startActivityForResult(Intent(this@ProductListActivity, FilterActivity::class.java),100)
        }

    }

    private fun initListPageVP() {
        val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.menshirts))
        mlistPageAdapter= ProductListAdapter(mSubCategoryList,object : ProductListAdapter.OnchildCallBack{
            override fun onChildClick(position: Int, product_id: String, product_token: String) {
                startActivityForResult(Intent(this@ProductListActivity, ProductDetailActivity::class.java).putExtra("product_id",product_id).putExtra("prod_token",product_token),100)
            }



            override fun onEmptyClick(position: Int) {

            }


        })





        listpage_RV.layoutManager = GridLayoutManager(this,2)
        listpage_RV.adapter = mlistPageAdapter


    }

    private fun initToolBar() {
        setSupportActionBar(product_list_Toolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text = category_title
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == 100) {
             filter_Id = data?.getStringExtra("filter_id")
            println(GsonBuilder().setPrettyPrinting().create().toJson(filter_Id))
            filterPresenter?.getFilterProducts(filter_Id.toString())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }




}