package com.ain.trading.ui.activity.product_detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.ProductBannerAdapter
import com.ain.trading.adapter.SimilarProductAdapter
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.home.HomeActivity
import com.ain.trading.ui.activity.login.AccountActivity
import com.ain.trading.ui.fragments.Fragment_cart
import com.ain.trading.ui.fragments.wishlist.FragmentWishList
import com.ain.trading.utils.AinPref
import com.ain.trading.utils.LoadingDialog
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar_center_title.*
import kotlinx.android.synthetic.main.product_mall_viewpager.*
import retrofit2.Call

class ProductDetailActivity : AppCompatActivity(),ProductView {
    override fun OnSearchPostalCodeSuccess(body: SearchPostalResponse) {
        if(body?.StatusCode==6000) {
           // Toast.makeText(this,body?.message,Toast.LENGTH_SHORT).show()
            greenLL.visibility=View.VISIBLE
            greenLLred.visibility=View.GONE
            faster_option_tv.text="Available"

        }
        else if((body?.StatusCode==6001)){
          //  Toast.makeText(this,body?.message,Toast.LENGTH_SHORT).show()
            greenLLred.visibility=View.VISIBLE
            greenLL.visibility=View.GONE
            faster_option_tv.text="Not available"
        }
    }

    override fun OnRelatedProductListSuccess(body: RelatedProducts) {
        mRelatedtList.clear()

        if (body?.StatusCode==6000) {

            similar_prdtBaseCL.visibility=View.VISIBLE
            mRelatedtList?.addAll(body?.data)
            msimilarProductActivity?.notifyDataSetChanged()

        }
        else if(body?.StatusCode==6001){
            similar_prdtBaseCL.visibility=View.GONE
        }
    }

    override fun OnAddCartListSuccess(body: CartAddedResponse) {
        if(body?.StatusCode==6000) {
            Toast.makeText(this,body?.message,Toast.LENGTH_SHORT).show()
        }
        else if((body?.StatusCode==6001)){
            Toast.makeText(this,body?.message,Toast.LENGTH_SHORT).show()
        }

    }
    override fun OnAddWishListSuccess(body: AddWishList?) {
        if(body?.StatusCode==6000) {
            Toast.makeText(this,body?.message,Toast.LENGTH_SHORT).show()
        }
        else if((body?.StatusCode==6001)){
            Toast.makeText(this,body?.message,Toast.LENGTH_SHORT).show()
        }

    }

    override fun OnProductDetailFetched(body: ProductDetails?) {

        mpImagetList?.clear()
        mpImagetList.removeAll(mpImagetList)
        if (body?.StatusCode==6000) {
            mpImagetList?.addAll(body?.data?.images)
            mProductBannerAdapter?.notifyDataSetChanged()

            name_prdt_tv.text=body?.data?.product_name
            price_prdt_tv.text=body?.data?.price+" SAR"
            product_code_No_tv.text=body?.data?.product_code
            product_desp_tv.text=body?.data?.ProductToDescription?.main_description
        }

    }

    override fun onError(error: String?) {
        this.showSnackBar(error.toString())
        //Toast.makeText(this,"Sorry ,Current product already exists in wishlist.Please try another",Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
loadingDialog.show()

    }

    protected fun hideKeyboard(view: View) {
        val `in` = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun hideProgress() {
loadingDialog.cancel()
    }

    var postal_code:String?=null
    var mpImagetList: MutableList<Images> = ArrayList<Images>()
    var mRelatedtList: MutableList<RelatesProducts> = ArrayList<RelatesProducts>()
    private lateinit var productDetailPresenter: ProductDetailPresenter
    private lateinit var loadingDialog: LoadingDialog
    var product_token: String?=null
    var product_id: String?=null
    var category_id: String?=null
    var msimilarProductActivity: SimilarProductAdapter? =null
    var mProductBannerAdapter: ProductBannerAdapter? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        setContentView(R.layout.activity_product_detail)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initToolBar()

        product_detailCL.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(view:View, ev:MotionEvent):Boolean {
                hideKeyboard(view)
                return false
            }
        })
        loadingDialog = LoadingDialog(this)
        productDetailPresenter = ProductDetailPresenter(this)

        if(intent.extras!=null&& intent.extras!!.containsKey("prod_token")){
            product_token=intent.getStringExtra("prod_token")
        }

        if(intent.extras!=null&& intent.extras!!.containsKey("product_id")){
            product_id=intent.getStringExtra("product_id")
        }
        if(intent.extras!=null&& intent.extras!!.containsKey("category_id")){
            category_id=intent.getStringExtra("category_id")
        }
           //postal_code=pincode_ET.text.toString()

        productDetailPresenter.getProductDetail(product_id)


        initViewPager()
        initSimilarProductVP()
        similarProd_RV.isNestedScrollingEnabled=false
        productBaseSV.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                //code will appear here
                return false
            }
        })

        productDetailPresenter.similarProducts(product_id,category_id)
        wishListBaseCL.setOnClickListener {
            if(AinPref.getGuestUser().equals("1"))
            {
                startActivity(Intent(this, AccountActivity::class.java))
                finish()
            }
            else{
                productDetailPresenter.addToWishList(product_id)
                add_wishList_Tv.setText(R.string.go_to_wish);
                val buttonTextWishList = add_wishList_Tv.getText().toString()


                if (buttonTextWishList==getString(R.string.go_to_wish)) {
                    wishListBaseCL.setOnClickListener {

                        var i =intent
                        intent.putExtra("key", 2)
                        i= Intent(this, HomeActivity::class.java).putExtra("from","product")
                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                     //   setResult(100, intent)
                        startActivity(i)
                        finish()
                      /*  var intent = intent

                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK*/

                      // startActivity(Intent(this, HomeActivity::class.java))

                    }}




            }

        }
        bagBaseCL.setOnClickListener {

            if(AinPref.getGuestUser().equals("1"))
            {
                startActivity(Intent(this, AccountActivity::class.java))
                finish()
            }
            else{
                productDetailPresenter.addToCartList(product_id)
                add_bag_Tv.setText(R.string.go_to_cart);
                val buttonTextcartList = add_bag_Tv.getText().toString()
                if (buttonTextcartList==getString(R.string.go_to_cart)) {
                    bagBaseCL.setOnClickListener {
                        /*val intent = intent
                        intent.putExtra("key", 3)
                        setResult(100, intent)
                        finish()*/
                        //   startActivity(Intent(this, FragmentWishList::class.java))
                        var i =intent
                        intent.putExtra("key", 3)
                        i= Intent(this, HomeActivity::class.java).putExtra("from","cart")
                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        //   setResult(100, intent)
                        startActivity(i)
                        finish()
                    }}
            }

        }


        postalEnter_id.setOnClickListener {
            postal_code=pincode_ET.text.toString()
            if (pincode_ET.text.toString().trim().isNotEmpty() ||
                pincode_ET.text.toString().trim().isNotBlank()) {
                productDetailPresenter.searchpostal(postal_code)
            }
      else  {
                Toast.makeText(this, "Please  enter pincode", Toast.LENGTH_SHORT).show()
            }

        }


    }


    private fun initSimilarProductVP() {
        val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.menshirts))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.menshirts))
        msimilarProductActivity= SimilarProductAdapter(mRelatedtList,object : SimilarProductAdapter.OnchildCallBack{
            override fun onServiceClickCallBack(
                position: Int,
                product_token: String,
                productIds: String,
                categoryIds: String
            ) {
                productDetailPresenter.getProductDetail(productIds)

                productDetailPresenter.similarProducts(productIds,categoryIds)
            }

            override fun onEmptyClick(position: Int) {

            }


        })

        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similarProd_RV.layoutManager = horizontalLayoutManager
        similarProd_RV.adapter = msimilarProductActivity

    }


    private fun initViewPager() {
        mProductBannerAdapter = ProductBannerAdapter(mpImagetList)
        product_ViewPager.setAdapter(mProductBannerAdapter)
// product_ViewPager.autoScroll=true
    }

    override fun onDestroy() {
        super.onDestroy()
        productDetailPresenter.detach()

    }


    private fun initToolBar() {
        setSupportActionBar(detailToolbar as Toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pink_back_24dp)
        titleTV.text = getString(R.string.product_detail)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }



}