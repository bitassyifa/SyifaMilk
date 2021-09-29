package com.projectassyifa.syifamilk.screen.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.cart.adapter.AdapterCart
import com.projectassyifa.syifamilk.data.cart.model.CartModel
import com.projectassyifa.syifamilk.data.cart.vm.CartVM
import com.projectassyifa.syifamilk.data.transaction.adapter.AdapterOrder
import com.projectassyifa.syifamilk.data.transaction.vm.TransactionVM
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_payment.*
import javax.inject.Inject

class PaymentActivity : AppCompatActivity() {
    var tampung :ArrayList<String> = ArrayList()
    var dataCart : ArrayList<String> = ArrayList()
//    var tampungCart :ArrayList<String> = ArrayList()
lateinit var adapterCart: AdapterCart

    val cartVM: CartVM by viewModels()



    @Inject
    lateinit var transactionVM: TransactionVM
    lateinit var adapterOrder: AdapterOrder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        (applicationContext as MyApp).applicationComponent.inject(this)

        transactionVM.data_transaksi_id?.observe(this, Observer {
            subtotal1.text = it.total_payment
            id_trans.text = it.id_transactions
        })
        transactionVM.get_trans_id(this,intent.getStringExtra("id_transaksi").toString())

        order_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        transactionVM.data_order?.observe(this, Observer {
            adapterOrder = AdapterOrder(it,this)
            order_list.adapter = adapterOrder
        })
        transactionVM.order_id(this,intent.getStringExtra("id_transaksi").toString())

        var viewPager = findViewById(R.id.viewPager) as ViewPager
        var tablayout = findViewById(R.id.tablayout) as TabLayout
        val fragmentAdapter = AdapterScreenPayment(supportFragmentManager)
        fragmentAdapter.addFragment(PaymentCash(),"Cash")
        fragmentAdapter.addFragment(PaymentFadipay(),"Fadipay")
        fragmentAdapter.addFragment(PaymentDeposit(),"Deposit")
        viewPager.adapter = fragmentAdapter
        tablayout.setupWithViewPager(viewPager)



    }
}