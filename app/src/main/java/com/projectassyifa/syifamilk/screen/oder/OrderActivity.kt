package com.projectassyifa.syifamilk.screen.oder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.cart.adapter.AdapterCart
import com.projectassyifa.syifamilk.data.cart.vm.CartVM
import com.projectassyifa.syifamilk.data.product.adapter.AdapterProduct
import com.projectassyifa.syifamilk.data.product.vm.ProductVM
import com.projectassyifa.syifamilk.screen.alert.LoadingBrown
import com.projectassyifa.syifamilk.screen.home.HomeActivity
import kotlinx.android.synthetic.main.activity_management_user.*
import kotlinx.android.synthetic.main.activity_order.*
import javax.inject.Inject

class OrderActivity : AppCompatActivity(), View.OnClickListener {

    var tampung :ArrayList<String> = ArrayList()

    var dataLogin : SharedPreferences? = null
    @Inject
    lateinit var productVM: ProductVM
    lateinit var adapterProduct: AdapterProduct


    val cartVM: CartVM by viewModels()
    lateinit var adapterCart: AdapterCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        (applicationContext as MyApp).applicationComponent.inject(this)

        dataLogin = this.getSharedPreferences(
            getString(R.string.datasave),
            Context.MODE_PRIVATE
        )
        refreshApp()
        val nama = dataLogin?.getString(
            getString(R.string.nama_pegawai),
            getString(R.string.default_value)
        )

        nama_kasir.text = nama

        val foto = dataLogin?.getString(
            getString(R.string.photo_user),
            getString(R.string.default_value)
        )
        var linkFoto= "http://202.62.9.138/syifamilk_api/uploads/foto_user/$foto"
        Glide
            .with(this)
            .load(linkFoto)
            .fitCenter()
            .placeholder(R.drawable.ic_profil)
            .into(profil_image);

        val loading = LoadingBrown(this)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                loading.isDismiss()
            }

        },2000)

        // list product
        R_listproduct.layoutManager = StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL)
        productVM.list_product?.observe(this, Observer {

            adapterProduct = AdapterProduct(it,this,cartVM)
            R_listproduct.adapter=adapterProduct
            adapterProduct.notifyDataSetChanged()
        })
        productVM.product(this)

        //search product
        search_product.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                println("KEYWORD 1 $keyword")
                R_listproduct.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
                productVM.list_product?.observe(this@OrderActivity, Observer {

                    adapterProduct = AdapterProduct(it,this@OrderActivity,cartVM)
                    R_listproduct.adapter=adapterProduct
                    adapterProduct.notifyDataSetChanged()
                })
                productVM.search_product(this@OrderActivity,keyword.toString())

                return false
            }

            override fun onQueryTextChange(keyword: String?): Boolean {
                println("KEYWORD 2 $keyword")
                R_listproduct.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
                productVM.list_product?.observe(this@OrderActivity, Observer {

                    adapterProduct = AdapterProduct(it,this@OrderActivity,cartVM)
                    R_listproduct.adapter=adapterProduct
                    adapterProduct.notifyDataSetChanged()
                })
                productVM.search_product(this@OrderActivity,keyword.toString())
                return false
            }

        })

//        cat_kopi.setOnClickListener {
//            R_listproduct.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
//            productVM.list_product?.observe(this, Observer {
//                println("INI IT $it")
//                adapterProduct = AdapterProduct(it,this,cartVM)
//                R_listproduct.adapter=adapterProduct
//                adapterProduct.notifyDataSetChanged()
//            })
//            productVM.by_category(this,1)
//
//        }
        val username = dataLogin?.getString(
            getString(R.string.username),
            getString(R.string.default_value)
        )

        list_cart.layoutManager = LinearLayoutManager(this)
       adapterCart = AdapterCart(cartVM.cartLiveData.value!!,cartVM,subtotal,btn_payment,this,username.toString())
        list_cart.adapter = adapterCart
        cartVM.cartLiveData.observe(this, Observer {


            adapterCart.notifyDataSetChanged()
        })
//            btn_payment.setOnClickListener(this)
    }

   fun refreshApp() {

        refreshLayout1.setOnRefreshListener {
            Toast.makeText(this,"Refresh", Toast.LENGTH_SHORT).show()
            finish();
            startActivity(getIntent());
        }
    }

    override fun onClick(v: View?) {
        when (v){
//            btn_payment ->{
//                val bundle = Bundle()
//                bundle.putStringArrayList("data1", tampung)
//
//
//                val intent = Intent(this, PaymentActivity::class.java)
//                intent.putExtras(bundle)
//                startActivity(intent)
//            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, HomeActivity::class.java))
    }
}

