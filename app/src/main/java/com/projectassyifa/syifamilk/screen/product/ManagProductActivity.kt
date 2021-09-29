package com.projectassyifa.syifamilk.screen.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.product.adapter.AdapterManagProduct
import com.projectassyifa.syifamilk.data.product.vm.ProductVM
import com.projectassyifa.syifamilk.screen.alert.LoadingBrown
import com.projectassyifa.syifamilk.screen.home.HomeActivity
import com.projectassyifa.syifamilk.screen.user.AdduserLayout
import kotlinx.android.synthetic.main.activity_manag_product.*
import kotlinx.android.synthetic.main.activity_order.*
import javax.inject.Inject

class ManagProductActivity : AppCompatActivity() {

    @Inject
    lateinit var productVM: ProductVM
    lateinit var adapterManagProduct: AdapterManagProduct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manag_product)
        (applicationContext as MyApp).applicationComponent.inject(this)
//        val loading = LoadingBrown(this)
//        loading.startLoading()
//        val handler = Handler()
//        handler.postDelayed(object :Runnable{
//            override fun run() {
//                loading.isDismiss()
//            }
//
//        },3000)
        refreshApp()
        data_produk.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        productVM.list_product?.observe(this, Observer {
            adapterManagProduct = AdapterManagProduct(it,this)
            data_produk.adapter = adapterManagProduct
        })
        productVM.product(this)

        btn_add_produk.setOnClickListener {
            var dialog = AddProductLayout()
            val bundle= Bundle()
            dialog.arguments = bundle


            dialog.show(supportFragmentManager, "customDialog")
        }


    }

    private fun refreshApp() {
        refresh.setOnRefreshListener {
            Toast.makeText(this,"Refresh", Toast.LENGTH_SHORT).show()
            finish();
            startActivity(getIntent());
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,HomeActivity::class.java))
    }
    fun finishMe() {
        finish()
        startActivity(getIntent());
    }
}