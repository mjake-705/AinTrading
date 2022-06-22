package com.ain.trading.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.BrandTLAdapter
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.branddetail.BrandDetailListActivity
import com.ain.trading.ui.activity.home.HomePresenter
import com.ain.trading.ui.activity.home.HomeView
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.frag_brand.*

class fragment_Brands:Fragment(),HomeView {
    override fun onCategoryBannerFetched(body: Banners?) {
    }

    override fun onBrandsListFetched(body: BrandDatailList?) {
    }

    override fun onBrandsFetched(body: BrandsList?) {
        mBrandsList?.clear()
        if (body != null) {
            mBrandsList?.addAll(body.data)
        }
        mBrandTLAdapter?.notifyDataSetChanged()
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

    override fun onError(error: String?) {
        activity?.showSnackBar(error.toString())
    }

    override fun showProgress() {
        loadingDialog.show()
    }

    override fun hideProgress() {
        loadingDialog.cancel()
    }
    var mBrandsList: MutableList<BrandsData> = ArrayList<BrandsData>()
    var mBrandTLAdapter: BrandTLAdapter? =null
    private lateinit var homePresenter: HomePresenter
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.frag_brand, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingDialog = LoadingDialog(activity = activity)
        homePresenter = HomePresenter(this)
        homePresenter.getBrands()
        inititemslist()

    }

    private fun inititemslist() {
        val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.brandimage))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.banner))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.brandimage))
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.shoesimage))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.women))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.shoesimage))
        mBrandTLAdapter= BrandTLAdapter(mBrandsList,object : BrandTLAdapter.OnchildCallBack{
            override fun onChildClick(position: Int, brand_id: String,  brand_title:String) {
                startActivity(Intent(activity, BrandDetailListActivity::class.java).putExtra("brand_id",brand_id))
            }

            override fun onEmptyClick(position: Int) {
            }


        })




        val horizontalLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        brandTLRc .layoutManager = horizontalLayoutManager
        brandTLRc.adapter = mBrandTLAdapter


    }

}


