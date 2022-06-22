package com.ain.trading.ui.activity.subcategory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.SubCategoryListAdapter
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.home.HomePresenter
import com.ain.trading.ui.activity.home.HomeView
import com.ain.trading.ui.activity.parentsubcategory.ParentSubcategoryActivity
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_sub_category_list.*
import kotlinx.android.synthetic.main.app_bar_center_title.*

class SubCategoryListActivity : AppCompatActivity(),HomeView{
    override fun onLatestProductfetched(body: ParentProductList?) {

    }

    override fun onBrandsListFetched(body: BrandDatailList?) {

    }

    override fun onCategoryBannerFetched(body: Banners?) {
    }

    override fun onBrandsFetched(body: BrandsList?) {
    }

    override fun onSubCategoryProductsFetched(body: ParentProductList?) {
    }

    override fun onSubCategoryDetailFetched(body: SubcategoryParentDetail?) {
    }

    override fun onCategoryDetailFetched(body: SubCategoryParent?) {
        mSubCategoryList.clear()
        if (body?.StatusCode==6000) {
            mSubCategoryList?.addAll(body?.data)
            mSubCategoryListAdapter?.notifyDataSetChanged()
        }
        else if(body?.StatusCode==6001)
        {
            this.showSnackBar(body?.message)
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

    override fun onError(error: String?) {
        this.showSnackBar(error.toString())
    }

    override fun showProgress() {
        loadingDialog.show()
    }

    override fun hideProgress() {
        loadingDialog.cancel()
    }

    var mSubCategoryList: MutableList<SubCatParent> = ArrayList<SubCatParent>()
    private lateinit var homePresenter: HomePresenter
    private lateinit var loadingDialog: LoadingDialog
    var mSubCategoryListAdapter: SubCategoryListAdapter? =null
    var category_id: String?=null
    var cat_title: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category_list)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        loadingDialog = LoadingDialog(activity = this)
        homePresenter = HomePresenter(this)
        if(intent.extras!=null&& intent.extras!!.containsKey("category_id")){
            category_id=intent.getStringExtra("category_id")
        }
        if(intent.extras!=null&& intent.extras!!.containsKey("cat_title")){
            cat_title=intent.getStringExtra("cat_title")
        }
        initToolBar()
        homePresenter.getCategoryDetail(category_id)
        inititemsVp()
    }

    private fun inititemsVp() {
        val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.menshirts))
        mSubCategoryListAdapter= SubCategoryListAdapter(mSubCategoryList,object : SubCategoryListAdapter.OnchildCallBack{
            override fun onChildClick(
                position: Int,
                category_id: String,
                categoryTitle: String
            ) {
                startActivity(Intent(this@SubCategoryListActivity, ParentSubcategoryActivity::class.java).putExtra("category_id",category_id).putExtra("cat_title",categoryTitle))
            }

            override fun onServiceClickCallBack(position: Int) {

            }



        })



        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sub_category_RV.layoutManager = horizontalLayoutManager
        sub_category_RV.adapter = mSubCategoryListAdapter




    }

    private fun initToolBar() {
        setSupportActionBar(sub_category_Toolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text = cat_title
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
