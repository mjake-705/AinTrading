package com.ain.trading.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.CartAdapter
import com.ain.trading.api.*
import com.ain.trading.customviews.ServiceObject
import com.ain.trading.ui.activity.AddressDetail.AddressDetailActivity
import com.ain.trading.ui.activity.product_detail.ProductDetailActivity

import com.ain.trading.ui.fragments.cart.CartPresenter
import com.ain.trading.ui.fragments.cart.CartView
import com.ain.trading.utils.*
import kotlinx.android.synthetic.main.app_bar_center_title.*
import kotlinx.android.synthetic.main.frag_cart.*

class Fragment_cart : Fragment(),CartView {
    internal interface Listener {
        fun oncartUpdate()
    }

    override fun OnAddWishListSuccess(body: AddWishList?) {

        if(body?.StatusCode==6000) {

            Toast.makeText(activity,body?.message,Toast.LENGTH_SHORT).show()
        }
        else if((body?.StatusCode==6001)){
            Toast.makeText(activity,body?.message,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onQuantityFetched(data: List<String>) {
        mqantity.clear()
        if (data != null) {
            mqantity?.addAll(data)

        }

    }

    override fun onCartUpdatedSuccesfully(body: UpdatedCart?) {
        if(body?.StatusCode==6000) {
            Toast.makeText(activity,body?.message, Toast.LENGTH_SHORT).show()
            cartPresenter?.getCart()
        }
        else if(body?.StatusCode==6001)
        {
            Toast.makeText(activity,body?.message, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCartRemovedSuccesfully(body: CartRemoveResponse?) {

        /*if(body?.message?.message?.equals("Success ,Selected item successfully deleted")!!)
        {
            cartPresenter.getCart()

        }
        else {
            cartPresenter.getCart()

        }*/


        if(body?.StatusCode==6000||body?.StatusCode==6002)
        {
            cartPresenter?.getCart()
        }
        else if((body?.StatusCode==6001)){
            Toast.makeText(activity,body?.message, Toast.LENGTH_SHORT).show()
        }





    }

    override fun onCartFetched(body: GetCartlistResponse?) {
        bag_total_prize.text=body?.data?.cart_total+" SAR"
        bag_discount_prize.text=body?.data?.total_discount+" SAR"
        tax_prize.text=body?.data?.tax+" SAR"
        order_prize_id.text=body?.data?.grand_total+" SAR"
        total_prize_id.text=body?.data?.grand_total+" SAR"
         totals=body?.data?.grand_total
        AinPref.setCartcount(body?.data?.total_count.toString())
        AinPref.getCartcount()
        (getActivity() as Listener).oncartUpdate()


        mCarttList.clear()


        if (body?.StatusCode==6000) {
            mCarttList?.addAll(body?.data?.items)
            mcartAdapter?.notifyDataSetChanged()
            topcartlistCL.visibility=View.VISIBLE
            cartemptyIV.visibility=View.GONE
            cartemtyTV.visibility=View.GONE
        }
        else if (body?.StatusCode==6001)
        {
            cartemtyTV.visibility=View.VISIBLE
            cartemptyIV.visibility=View.VISIBLE
            topcartlistCL.visibility=View.GONE
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
var totals:String?=null
    var data: Int? = null
    private var selectedZone:String?=null
    private lateinit var pickQntity: CustomListCountryDialog<String>
    var mqantity:MutableList<String> = ArrayList<String>()
    var mCarttList: MutableList<Item> = ArrayList<Item>()
    var mcartAdapter: CartAdapter? =null
    lateinit var logOutDialog: AinAlertDialog
 var cartPresenter: CartPresenter?=null
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.frag_cart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingDialog = LoadingDialog(activity = activity)
        cartPresenter= CartPresenter(this)
        initToolBar()
        cartemptyIV.visibility=View.GONE
        cartemtyTV.visibility=View.GONE
        if(AinPref.getGuestUser().equals("3"))
        {

        }
        else if(AinPref.getGuestUser().equals("0")){
            cartPresenter?.getCart()
        }
        inititemslist()
        addtocartTV.setOnClickListener {

            startActivity(Intent(activity, AddressDetailActivity::class.java).putExtra("shipping_rate",totals))


        }


    }

    private fun inititemslist() {

        val value : MutableList<ServiceObject> = ArrayList()
        value.add(ServiceObject("\$Electronics","\$55",R.drawable.shoesimage))
        value.add(ServiceObject("\$Fashion","\$55",R.drawable.women))
        value.add(ServiceObject("\$Home Furniture","\$55",R.drawable.shoesimage))

        mcartAdapter= CartAdapter(mCarttList,object : CartAdapter.OnchildCallBack{
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

            override fun onMoveToWishlist(position: Int, productId: String) {
             cartPresenter?.addToWishList(productId)
            }

            override fun onServiceClickQuantityCallBack(position: Int, product_id: String) {

            }
            /*  override fun onServiceClickQuantityCallBack(position: Int, product_id: String) {

              }*/

            override fun onUpdate(
                position: Int,
                edit_count: String,
                productId: Int
            ) {
                cartPresenter?.updateCart(edit_count,productId)
            }

            override fun onServiceClickCallBack(position: Int, cart_id: String) {

                logOutDialog = AinAlertDialog(activity!!)

                logOutDialog.show(getString(R.string.remove), getString(R.string.sure_remove), getString(R.string.yes),getString(R.string.no),
                    positiveButtonListener = View.OnClickListener {
                        logOutDialog.dismiss()
                        cartPresenter?.removeCartitem(cart_id)
                    },negativeButtonListener = View.OnClickListener {
                        logOutDialog.dismiss()
                    })



               // cartPresenter?.removeCartitem(cart_id)
            }


        })

        val horizontalLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        cartrecycle_id.layoutManager = horizontalLayoutManager
        cartrecycle_id.adapter = mcartAdapter


    }

    override fun onResume() {
        super.onResume()
        if(AinPref.getGuestUser().equals("3"))
        {

        }
        else if(AinPref.getGuestUser().equals("0")){
            cartPresenter?.getCart()
        }
    }

    fun checkEmpty() {
        cartemptyIV.visibility = (if (mcartAdapter?.itemCount == 0) View.VISIBLE else View.GONE)
        topcartlistCL.visibility=(if (mcartAdapter?.itemCount == 0) View.GONE else View.VISIBLE)
        cartemtyTV.visibility=(if (mcartAdapter?.itemCount == 0) View.VISIBLE else View.GONE)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            cartPresenter?.getCart()
        }
    }
    private fun initToolBar() {
        (activity as AppCompatActivity).setSupportActionBar(cartToolbar as Toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = ""

        titleTV.text =getString(R.string.cartlist)
    }

}