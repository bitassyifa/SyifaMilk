package com.projectassyifa.syifamilk.screen.payment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.cart.adapter.AdapterCart
import com.projectassyifa.syifamilk.data.cart.model.CartModel
import com.projectassyifa.syifamilk.data.cart.vm.CartVM
import com.projectassyifa.syifamilk.data.payment.adapter.AdapterPaymentMethod
import com.projectassyifa.syifamilk.data.payment.model.PaymentModel
import com.projectassyifa.syifamilk.data.payment.vm.PaymentVM
import com.projectassyifa.syifamilk.data.transaction.adapter.AdapterOrder
import com.projectassyifa.syifamilk.data.transaction.vm.TransactionVM
import com.projectassyifa.syifamilk.screen.home.HomeActivity
import com.projectassyifa.syifamilk.screen.oder.OrderActivity
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_payment.*
import javax.inject.Inject

class PaymentActivity : AppCompatActivity() {

    var pay_method : String? = null
    lateinit var adapterCart: AdapterCart



    val cartVM: CartVM by viewModels()

    @Inject
    lateinit var paymentVM : PaymentVM
    lateinit var adapterPaymentMethod : AdapterPaymentMethod

    @Inject
    lateinit var transactionVM: TransactionVM
    lateinit var adapterOrder: AdapterOrder

    var dataLogin : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        (applicationContext as MyApp).applicationComponent.inject(this)

        dataLogin = this.getSharedPreferences(
            getString(R.string.datasave),
            Context.MODE_PRIVATE
        )

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



        // list method payment
        list_mpay.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        paymentVM.p_method?.observe(this, Observer {
            adapterPaymentMethod = AdapterPaymentMethod(it,this)

            list_mpay.adapter = adapterPaymentMethod


            adapterPaymentMethod.setOnItemClickLitener(object :AdapterPaymentMethod.OnItemClickListener{
                override fun onItemClick(view: View, position: Int) {
                    adapterPaymentMethod.setSelection(position)

                   pay_method = adapterPaymentMethod.list_pmethod[position].id_payment_method
                    println("PAY METHOD $pay_method")

                }

            })
        })

        paymentVM.get_payment_method(this)

        val username = dataLogin?.getString(
            getString(R.string.username),
            getString(R.string.default_value)
        )

        btn_payment_proses.setOnClickListener {
            var data_payment = PaymentModel()
            data_payment.id_transaction = id_trans.text.toString()
            data_payment.payment_method = pay_method.toString()
            data_payment.customer_id = pay_idcustomer.text.toString()
            data_payment.customer_name = pay_namecustomer.text.toString()
            data_payment.total_payment = subtotal1.text.toString().toInt()
            data_payment.created_by = username.toString()

//            println("DATA PAYMENT ID : ${data_payment.id_transaction}")
//            println("DATA PAYMENT ID CUST :  ${data_payment.customer_id}")
//            println("DATA PAYMENT NAMA CUST :  ${data_payment.customer_name}")
//            println("DATA PAYMENT METHOD PAY :  ${data_payment.payment_method}")
//            println("DATA PAYMENT TOTAL PAY :  ${data_payment.total_payment}")
//            println("DATA PAYMENT CREATED BY  :  ${data_payment.created_by}")
            paymentVM.responseAPI?.observe(this, Observer {
                if (it.status){
                    finish()
                    startActivity(Intent(this,OrderActivity::class.java))
                }
            })
            paymentVM.post_payment(this,data_payment)

        }


    }

}