package com.ain.trading.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.CategoryTLAdapter
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.home.HomePresenter
import com.ain.trading.ui.activity.home.HomeView
import com.ain.trading.ui.activity.subcategory.SubCategoryListActivity
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.checkNetworkConnectivity
import com.ain.trading.utils.showSnackBar

import kotlinx.android.synthetic.main.frag_category.*

class fragment_Category:Fragment(),HomeView {
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
    }

    override fun onCategoryFetched(body: Maincategory?) {
        mFragmentList?.clear()
        if (body != null) {
            mFragmentList?.addAll(body.data)
        }
        mCategoryTLAdapter?.notifyDataSetChanged()
    }

    override fun onBannersFetched(body: Banners?) {
    }

    override fun onBrandinFocusfetched(body: Banners?) {
    }



    override fun OnAddCartListSuccess(body: CartAddedResponse) {
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
    var mFragmentList: MutableList<Data> = ArrayList<Data>()
    private lateinit var homePresenter: HomePresenter
    private lateinit var loadingDialog: LoadingDialog
    var mCategoryTLAdapter: CategoryTLAdapter? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.frag_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        loadingDialog = LoadingDialog(activity = activity)
        homePresenter = HomePresenter(this)
        if (activity?.checkNetworkConnectivity()!!)
        {
            homePresenter.getCateGories()
        }
        inititemslist()
    }


    private fun inititemslist() {

        val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.shoesimage))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.women))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.shoesimage))
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.shoesimage))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.women))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.shoesimage))
        mCategoryTLAdapter= CategoryTLAdapter(mFragmentList,object : CategoryTLAdapter.OnchildCallBack{
            override fun onServiceClickCallBack(position: Int) {
            }


            override fun onChildClick(
                position: Int,
                category_id: String,
                categoryTitle: String
            ) {
                startActivity(Intent(activity, SubCategoryListActivity::class.java).putExtra("category_id",
                    category_id).putExtra("cat_title",categoryTitle))
            }

        })









        val horizontalLayoutManager = GridLayoutManager(activity, 2)
        categoryTLRecylerv.layoutManager= horizontalLayoutManager
        categoryTLRecylerv.adapter = mCategoryTLAdapter


    }

}





