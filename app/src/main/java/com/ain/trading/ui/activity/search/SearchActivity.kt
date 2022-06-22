package com.ain.trading.ui.activity.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ain.trading.R
import com.ain.trading.adapter.SearchAdapterClass
import com.ain.trading.api.Children
import com.ain.trading.api.SearchData
import com.ain.trading.api.SearchResponse
import com.ain.trading.ui.activity.product_detail.ProductDetailActivity
import com.ain.trading.utils.checkNetworkConnectivity
import com.ain.trading.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_search.*
import com.ain.trading.utils.LoadingDialog


class SearchActivity : AppCompatActivity(),SearchView {
    override fun onSearchDataFetched(body: SearchResponse?) {
       searchList?.clear()
        if (body?.StatusCode==6000) {
            searchList?.addAll(body.data)
            mSearchAdapter?.notifyDataSetChanged()
            //mSearchAdapter?.notifyDataSetChanged()
        }
        else if(body?.StatusCode == 6001)
        {
            searchList?.clear()
            mSearchAdapter?.notifyDataSetChanged()
            Toast.makeText(this,body?.message, Toast.LENGTH_SHORT).show()
        }
       /* if (body?.StatusCode == 6000){
            searchList?.addAll(body.data)
       }
       mSearchAdapter?.notifyDataSetChanged()*/
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
    private lateinit var loadingDialog: LoadingDialog
    var searchList: MutableList<SearchData> = ArrayList<SearchData>()
    private lateinit var mSearchActivityPresenter: SearchActivityPresenter
    var mSearchAdapter: SearchAdapterClass? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        loadingDialog = LoadingDialog(this)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mSearchActivityPresenter = SearchActivityPresenter(this)




              //  mSearchActivityPresenter.getSearchData()
                inititemslist()


        sreachET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (checkNetworkConnectivity()) {
                    if (sreachET.length()>=0) {
                        mSearchActivityPresenter?.getSearchData(sreachET.text.toString())
                        sreach_listRV.visibility= View.VISIBLE
                    }
                }
            }
        })





    }

    private fun inititemslist() {


        mSearchAdapter= SearchAdapterClass (searchList,object : SearchAdapterClass.OnchildCallBack{
            override fun onServiceClickCallBack(
                position: Int,
                product_token: String,
                productId: String,
                categoryId: String
            ) {
                startActivity(Intent(applicationContext, ProductDetailActivity::class.java).putExtra("prod_token", product_token).putExtra("product_id",productId).putExtra("category_id",categoryId))
            }

            override fun onChildClick(position: Int, mChildrenList: MutableList<Children>) {

            }




        })









        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sreach_listRV .layoutManager = horizontalLayoutManager
        sreach_listRV.adapter = mSearchAdapter


    }




}
