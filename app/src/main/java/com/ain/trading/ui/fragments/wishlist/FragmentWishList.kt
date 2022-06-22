package com.ain.trading.ui.fragments.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.WishlistAdapter
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.customviews.ServiceWishListclass
import com.ain.trading.ui.activity.home.HomePresenter
import com.ain.trading.ui.activity.home.HomeView
import com.ain.trading.ui.activity.product_detail.ProductDetailActivity
import com.ain.trading.utils.AinAlertDialog
import com.ain.trading.utils.AinPref
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.app_bar_center_title.*
import kotlinx.android.synthetic.main.frag_wishlist.*
import kotlinx.android.synthetic.main.wish_list.*

class FragmentWishList : Fragment(),WishlistView, HomeView {
    override fun onLatestProductfetched(body: ParentProductList?) {
    }
    override fun onBrandsListFetched(body: BrandDatailList?) {
    }
    override fun onCategoryBannerFetched(body: Banners?) {
    }
    override fun onCategoryFetched(body: Maincategory?) {}
    override fun onBannersFetched(body: Banners?) {
    }
    override fun onBrandinFocusfetched(body: Banners?) {
    }
    override fun OnAddCartListSuccess(body: CartAddedResponse) {
        if(body?.StatusCode==6000) {
            Toast.makeText(activity,body?.message,Toast.LENGTH_SHORT).show()
        }
        else if((body?.StatusCode==6001)){
            Toast.makeText(activity,body?.message,Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCategoryDetailFetched(body: SubCategoryParent?) {
    }
    override fun onSubCategoryDetailFetched(body: SubcategoryParentDetail?) {
    }

    override fun onSubCategoryProductsFetched(body: ParentProductList?) {
    }
    override fun onBrandsFetched(body: BrandsList?) {
    }
    override fun onWishlistRemoveSuccess(body: RemoveWishlistNew?) {
        if(body?.StatusCode==6000||body?.StatusCode==6002)
        {
            wishlistPresenter?.getWishList()
        }
        else if((body?.StatusCode==6001)){
            Toast.makeText(activity,body?.message, Toast.LENGTH_SHORT).show()
        }
    }
    override fun OnWishListFetched(body: WishListNew?) {
        mFragmentList?.clear()
        if (body?.StatusCode==6000) {
            mFragmentList?.addAll(body?.data!!)
            wishlistclasseAdapter!!.notifyDataSetChanged()
            wishlist_recycler_id.visibility=View.VISIBLE
            wishlistemptyIV.visibility=View.GONE
            wishemtyTV.visibility=View.GONE
        }
        else if (body?.StatusCode==6001)
        {
            wishlistemptyIV.visibility=View.VISIBLE
            wishlist_recycler_id.visibility=View.GONE
            wishemtyTV.visibility=View.VISIBLE
        }
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
    lateinit var logOutDialog: AinAlertDialog
    private lateinit var homePresenter: HomePresenter
    var product_id: Int? = null
    var wishlistclasseAdapter: WishlistAdapter? =null
     var wishlistPresenter: WishlistPresenter?=null
    private lateinit var loadingDialog: LoadingDialog
    var mFragmentList: MutableList<WishListData> = ArrayList<WishListData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_wishlist, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingDialog = LoadingDialog(activity = activity)
        wishlistPresenter = WishlistPresenter(this)
        homePresenter = HomePresenter(this)

initToolBar()
        // product_id=removeitem?.product_id
        if(AinPref.getGuestUser().equals("3"))
        {

        }
        else if(AinPref.getGuestUser().equals("0")){
            wishlistPresenter?.getWishList()
        }
        initWishListVP()
/*
wishListRemove.setOnClickListener{
    wishlistPresenter.removeWishList(product_id.toString())
}
*/
    }
    private fun initToolBar() {
        (activity as AppCompatActivity).setSupportActionBar(wish_listToolbar as Toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = ""
     //   (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
       // (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text =getString(R.string.wishlist)
    }

    private fun  initWishListVP() {
        val value : MutableList<ServiceWishListclass> = ArrayList()
        //val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceWishListclass("\$Electronics","\$55",R.drawable.shoesimage))
        value.add(ServiceWishListclass("\$Fashion","\$55",R.drawable.women))
        value.add(ServiceWishListclass("\$Home Furniture","\$55",R.drawable.shoesimage))

        wishlistclasseAdapter= WishlistAdapter(mFragmentList,object :WishlistAdapter.OnchildCallBack{
            override fun onItemClickCallback(
                position: Int,
                product_id: String,
                category_id: String
            ) {
                startActivityForResult(
                    Intent(activity, ProductDetailActivity::class.java).putExtra(
                        "product_id",
                        product_id
                    ).putExtra("category_id", category_id),100)
            }

            override fun onServiceClickCallBackAddCart(position: Int, wishListId: String) {
               homePresenter.addToCartList(wishListId)
            }

            override fun onServiceClickCallBack(position: Int, wishListId: String) {

                 logOutDialog = AinAlertDialog(activity!!)

                logOutDialog.show(getString(R.string.remove), getString(R.string.sure_remove), getString(R.string.yes),getString(R.string.no),
                    positiveButtonListener = View.OnClickListener {
                        logOutDialog.dismiss()
                        wishlistPresenter?.removeWishList(wishListId)
                    },negativeButtonListener = View.OnClickListener {
                        logOutDialog.dismiss()
                    })
                            }
        })

        val horizontalLayoutManager = GridLayoutManager(activity, 2)
        wishlist_recycler_id.layoutManager= horizontalLayoutManager
        wishlist_recycler_id.adapter = wishlistclasseAdapter
    }


    override fun onResume() {
        super.onResume()
       if(AinPref.getGuestUser().equals("3"))
        {
        }
        else if(AinPref.getGuestUser().equals("0")){
            wishlistPresenter?.getWishList()
        }

    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            wishlistPresenter?.getWishList()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
        }
        return super.onOptionsItemSelected(item)
    }

}