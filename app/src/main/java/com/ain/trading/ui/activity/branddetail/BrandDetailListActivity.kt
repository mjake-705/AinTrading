package com.ain.trading.ui.activity.branddetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.BrandDetailAdapter
import com.ain.trading.api.*
import com.ain.trading.ui.activity.home.HomePresenter
import com.ain.trading.ui.activity.home.HomeView
import com.ain.trading.ui.activity.product_detail.ProductDetailActivity
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_brand_detail_list.*
import kotlinx.android.synthetic.main.app_bar_center_title.*

class BrandDetailListActivity : AppCompatActivity(), HomeView {
    override fun onCategoryBannerFetched(body: Banners?) {
    }

    override fun onBrandsListFetched(body: BrandDatailList?) {
        mBrandList.clear()

        if (body?.StatusCode==6000) {
            mBrandList?.addAll(body?.data)
            mlistPageAdapter?.notifyDataSetChanged()
            nobrandtext.visibility= View.GONE

            br_listpage_RV.visibility=View.VISIBLE
        }
        else if(body?.StatusCode==6001)
        {
            nobrandtext.visibility= View.VISIBLE

            br_listpage_RV.visibility=View.GONE
        }

    }

    override fun onCategoryFetched(body: Maincategory?) {
    }

    override fun onBannersFetched(body: Banners?) {
    }

    override fun onBrandinFocusfetched(body: Banners?) {
    }

    override fun onLatestProductfetched(body: ParentProductList?) {
    }

   override fun OnAddCartListSuccess(body: CartAddedResponse) {
   }

    override fun onCategoryDetailFetched(body: SubCategoryParent?) {
    }

    override fun onSubCategoryDetailFetched(body: SubcategoryParentDetail?) {
    }

    override fun onSubCategoryProductsFetched(body: ParentProductList?) {
    }

    override fun onBrandsFetched(body: BrandsList?) {
    }

    override fun onError(error: String?) {
        this.showSnackBar(error.toString())
    }

    override fun showProgress() {
        loadingDialog.show()
    }

    override fun hideProgress() {
        loadingDialog.cancel()
    }
    var category_title: String?=null
    var brand_id: String?=null
    var mBrandList: MutableList<BrandDetailData> = ArrayList<BrandDetailData>()
    var category_token: String?=null
    var mlistPageAdapter: BrandDetailAdapter? =null
    private lateinit var homePresenter: HomePresenter
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand_detail_list)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        loadingDialog = LoadingDialog(activity = this)
        homePresenter = HomePresenter(this)
        if(intent.extras!=null&& intent.extras!!.containsKey("brand_id")){
            brand_id=intent.getStringExtra("brand_id")
            category_title=intent.getStringExtra("brand_title")
        }

        homePresenter.getBrandsDetails(brand_id)
        initListPageVP()
        initToolBar()
    }

    private fun initListPageVP() {

        mlistPageAdapter= BrandDetailAdapter(mBrandList,object : BrandDetailAdapter.OnchildCallBack{
            override fun onChildClick(
                position: Int,
                product_id: String,
                product_token: String,
                categoryId: String
            ) {
                startActivityForResult(Intent(this@BrandDetailListActivity, ProductDetailActivity::class.java).putExtra("product_id",product_id).putExtra("prod_token",product_token).putExtra("category_id",categoryId),100)
            }



            override fun onEmptyClick(position: Int) {

            }

        })


        br_listpage_RV.layoutManager = GridLayoutManager(this,2)
        br_listpage_RV.adapter = mlistPageAdapter
    }



    private fun initToolBar() {
        setSupportActionBar(brand_aboutToolbar as Toolbar)
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
}
