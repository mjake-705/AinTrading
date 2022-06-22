package com.ain.trading.ui.activity.parentsubcategory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.SubCategoryParentListAdapter
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.home.HomePresenter
import com.ain.trading.ui.activity.home.HomeView
import com.ain.trading.ui.activity.product_list.ProductListActivity
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_parent_subcategory.*
import kotlinx.android.synthetic.main.app_bar_center_title.*

class ParentSubcategoryActivity : AppCompatActivity(),HomeView {
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
        mSubCategoryList.clear()
        if (body?.StatusCode==6000) {
            mSubCategoryList.addAll(body.data)
            mSubParentListAdapter?.notifyDataSetChanged()
        }
        else if(body?.StatusCode==6001){
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

    override fun onCategoryDetailFetched(body: SubCategoryParent?) {

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

    var mSubCategoryList: MutableList<SubparentCategoryData> = ArrayList<SubparentCategoryData>()
    private lateinit var homePresenter: HomePresenter
    private lateinit var loadingDialog: LoadingDialog
    var category_id: String?=null
    var cat_title: String?=null
    var mSubParentListAdapter: SubCategoryParentListAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_subcategory)
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

        homePresenter.getSubcategoryDetaiil(category_id)
        inititemsParent()
    }

    private fun inititemsParent() {
        val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.menshirts))
        mSubParentListAdapter= SubCategoryParentListAdapter(mSubCategoryList,object : SubCategoryParentListAdapter.OnchildCallBack{
            override fun onChildClick(
                position: Int,
                category_title: String,
                category_id: String
            ) {
               startActivity(Intent(this@ParentSubcategoryActivity, ProductListActivity::class.java).putExtra("category_id",category_id).putExtra("category_title",category_title))
            }



            override fun onServiceClickCallBack(position: Int) {

            }



        })


        val horizontalLayoutManager = GridLayoutManager(this, 2)
        Parent_sub_category_RV.layoutManager= horizontalLayoutManager
        Parent_sub_category_RV.adapter = mSubParentListAdapter


    }



    private fun initToolBar() {
        setSupportActionBar(aboutToolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text =cat_title
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
