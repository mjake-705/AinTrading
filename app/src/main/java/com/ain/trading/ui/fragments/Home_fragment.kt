package com.ain.trading.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.*
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.Notifications.NotificationActivity
import com.ain.trading.ui.activity.branddetail.BrandDetailListActivity
import com.ain.trading.ui.activity.category.CategoriesDetailActivity
import com.ain.trading.ui.activity.home.HomePresenter
import com.ain.trading.ui.activity.home.HomeView
import com.ain.trading.ui.activity.product_detail.ProductDetailActivity
import com.ain.trading.ui.activity.search.SearchActivity
import com.ain.trading.ui.activity.subcategory.SubCategoryListActivity
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.checkNetworkConnectivity
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.home_mall_view_pager.*

class Home_fragment : Fragment(), HomeView {


    override fun onCategoryBannerFetched(body: Banners?) {

        mCatbannertList?.clear()
        if (body?.StatusCode==6000) {
            mCatbannertList?.addAll(body?.data.images)
            mCatBannerImageAdapter!!.notifyDataSetChanged()
        }
        else if(body?.StatusCode==6001)
        {
            Toast.makeText(activity,body?.message,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBrandsListFetched(body: BrandDatailList?) {
if(body?.StatusCode==6000)
{

}
        else if(body?.StatusCode==6001)
{

}
    }

    override fun onLatestProductfetched(body: ParentProductList?) {
        mLatestList?.clear()
        if (body?.StatusCode==6000) {
            mLatestList?.addAll(body?.data)
            mRecomendedAdapter!!.notifyDataSetChanged()
        }
        else if(body?.StatusCode==6001)
        {
            Toast.makeText(activity,body?.message,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBrandsFetched(body: BrandsList?) {
        mBrandsList?.clear()
        if (body?.StatusCode==6000) {
            mBrandsList?.addAll(body?.data)
         //   mBrandFocusAdapter?.notifyDataSetChanged()
        }
        else if(body?.StatusCode==6001)
        {
            Toast.makeText(activity,body?.message,Toast.LENGTH_SHORT).show()
        }

    }

    override fun onSubCategoryProductsFetched(body: ParentProductList?) {
    }

    override fun onSubCategoryDetailFetched(body: SubcategoryParentDetail?) {
    }

    override fun onCategoryDetailFetched(body: SubCategoryParent?) {
    }

    override fun OnAddCartListSuccess(body: CartAddedResponse) {
        /* if(body?.message?.StatusCode==6000) {
             Toast.makeText(activity,body?.message?.message, Toast.LENGTH_SHORT).show()
         }
         else if((body?.message?.StatusCode==6001)){
             Toast.makeText(activity,body?.message?.message, Toast.LENGTH_SHORT).show()
         }*/
    }


    override fun onBrandinFocusfetched(body: Banners?) {
        mbbrandinFocusList?.clear()
        if (body != null) {
            mbbrandinFocusList?.addAll(body?.data.images)
            mBrandFocusAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onBannersFetched(body: Banners?) {
        mbannertList?.clear()
        if (body != null) {
            mbannertList?.addAll(body?.data.images)
            mBannerImageAdapter?.notifyDataSetChanged()
        }
    }

    override fun onCategoryFetched(body: Maincategory?) {
        mFragmentList?.clear()
        if (body != null) {
            mFragmentList?.addAll(body.data)
        }
        TopFeddImageAdapter?.notifyDataSetChanged()
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
    private lateinit var homePresenter: HomePresenter
    private lateinit var loadingDialog: LoadingDialog
    var mBannerImageAdapter: BannerAdapter? = null
    var mCatBannerImageAdapter: CategoryBannerAdapter? = null
    var TopFeddImageAdapter: TopFeedsAdapter? = null
    var mBrandFocusAdapter: BrandTLAdapter? = null
    var mRecomendedAdapter: RecomendedAdapter? = null
    var mFragmentList: MutableList<Data> = ArrayList<Data>()
    var mbannertList: MutableList<Image> = ArrayList<Image>()
    var mCatbannertList: MutableList<Image> = ArrayList<Image>()
    var mLatestList: MutableList<Parentproduct> = ArrayList<Parentproduct>()

    var mbbrandinFocusList: MutableList<Image> = ArrayList<Image>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingDialog = LoadingDialog(activity = activity)
        homePresenter = HomePresenter(this)
        if (activity?.checkNetworkConnectivity()!!) {
            homePresenter.getCateGories()
            homePresenter.getBanners()
            homePresenter.getbrandinFocus()
            homePresenter.getCategoryBanner()
            homePresenter.getLatestProduct()
            homePresenter.getBrands()


        }
        initViewPager()
        initCatBannerVp()
        initTopFeedVP()
        initBrandFocusVP()
        initRecomendedVP()
        mallFeedRV.setOnClickListener {
            startActivity(Intent(activity, CategoriesDetailActivity::class.java))
        }
       /* catRv.setOnClickListener {
            startActivity(Intent(activity, CategoriesDetailActivity::class.java).putExtra("from","nothome"))
        }*/
        shopBaseSV.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                //code will appear here
                return false
            }
        })

        sreach_icon_id.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }

        bell_icon_id.setOnClickListener {
            startActivity(Intent(activity, NotificationActivity::class.java))
        }
    }

    private fun initCatBannerVp() {

        mCatBannerImageAdapter =
            CategoryBannerAdapter(mCatbannertList, object : CategoryBannerAdapter.OnchildCallBack {
                override fun onChildClick(position: Int) {
                    startActivity(Intent(activity, CategoriesDetailActivity::class.java).putExtra("from","home"))
                }

                override fun onEmptyClick(position: Int) {
                }

            })
        val horizontalLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        catRv.layoutManager = horizontalLayoutManager
        catRv.adapter = mCatBannerImageAdapter
    }

    private fun initRecomendedVP() {
        val value: MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics", "\$55", R.drawable.shoesimage))
        value.add(ServiceObject("\$Fashion", "\$55", R.drawable.women))
        value.add(ServiceObject("\$Home Furniture", "\$55", R.drawable.shoesimage))
        mRecomendedAdapter =
            RecomendedAdapter(mLatestList, object : RecomendedAdapter.ServiceCallback {
                override fun onServiceClickCallBack(
                    position: Int,
                    product_token: String,
                    productId: String,
                    categoryId: String
                ) {
                    startActivityForResult(Intent(activity, ProductDetailActivity::class.java).putExtra("prod_token", product_token).putExtra(
                        "product_id",
                        productId
                    ).putExtra("category_id", categoryId),100)

                }

                override fun onAddCart(position: Int, productId: String) {
                    //    homePresenter.addToCartList(productId)
                }

            })

        val horizontalLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recomended_Rv.layoutManager = horizontalLayoutManager
        recomended_Rv.adapter = mRecomendedAdapter

    }

    private fun initBrandFocusVP() {
        /* val value : MutableList<ServiceObject> = ArrayList()
         value.add(ServiceObject("\$Electronics","\$55",R.drawable.brandimage))
         value.add(ServiceObject("\$Fashion","\$55",R.drawable.brandimageone))
         value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.brandimage))*/
        mBrandFocusAdapter= BrandTLAdapter(mBrandsList,object : BrandTLAdapter.OnchildCallBack{
            override fun onChildClick(position: Int, brand_id: String,brand_title:String) {
                startActivityForResult(Intent(activity, BrandDetailListActivity::class.java).putExtra("brand_id",brand_id).putExtra("brand_title",brand_title),100)
            }

            override fun onEmptyClick(position: Int) {
            }
        })

        val horizontalLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        brandRv.layoutManager = horizontalLayoutManager
        brandRv.adapter = mBrandFocusAdapter

      /*  mBrandFocusAdapter =
            BrandFocusAdapter(mbbrandinFocusList, object : BrandFocusAdapter.OnchildCallBack {
                override fun onChildClick(position: Int) {
                    startActivity(
                        Intent(
                            activity,
                            CategoriesDetailActivity::class.java
                        ).putExtra("from", "home")
                    )
                }

                override fun onEmptyClick(position: Int) {
                }

            })
        val horizontalLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        brandRv.layoutManager = horizontalLayoutManager
        brandRv.adapter = mBrandFocusAdapter*/
    }


    private fun initViewPager() {

        mBannerImageAdapter = BannerAdapter(mbannertList)
        banner_ViewPager.setAdapter(mBannerImageAdapter)
        banner_ViewPager.autoScroll = true
    }

    private fun initTopFeedVP() {
        val value: List<Data> = ArrayList()
        /* value.add(ServiceObject("\$Electronics","\$55",R.drawable.electroics_icon))
         value.add(ServiceObject("\$Fashion","\$55",R.drawable.furniture_icon))
         value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.electroics_icon))
         value.add(ServiceObject("\$Electronics","\$55",R.drawable.electroics_icon))
         value.add(ServiceObject("\$Fashion","\$55",R.drawable.furniture_icon))
         value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.electroics_icon))
         value.add(ServiceObject("\$Electronics","\$55",R.drawable.electroics_icon))
         value.add(ServiceObject("\$Fashion","\$55",R.drawable.furniture_icon))
         value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.electroics_icon))*/


        TopFeddImageAdapter =
            TopFeedsAdapter(mFragmentList, object : TopFeedsAdapter.OnchildCallBack {
                override fun onEmptyClick(position: Int) {
                    /*logOutDialog = FitnessAlertDialog(activity!!)

                    logOutDialog.show(getString(R.string.alert), getString(R.string.noSubCategory), getString(R.string.ok) ,
                        positiveButtonListener = View.OnClickListener {
                            logOutDialog.dismiss()



                        })*/
                }

                override fun onChildClick(
                    position: Int,
                    category_id: String,
                    categoryTitle: String
                ) {

                    // intent.putExtra("blogData", blog as Serializable)
                   /* startActivity(
                        Intent(
                            activity,
                            CategoriesDetailActivity::class.java
                        ).putExtra("from", "top")
                    )*/
                    // startActivity(Intent(activity, FeedsDetailActivity::class.java). putExtra("ARRAYLIST", mChildrenList as Serializable))
                    startActivity(Intent(activity, SubCategoryListActivity::class.java).putExtra("category_id",
                        category_id).putExtra("cat_title",categoryTitle))

                }

            })
        val horizontalLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mallFeedRV.layoutManager = horizontalLayoutManager
        mallFeedRV.adapter = TopFeddImageAdapter
    }


}