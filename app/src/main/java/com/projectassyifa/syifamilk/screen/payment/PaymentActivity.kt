package com.projectassyifa.syifamilk.screen.payment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.application.isradeleon.thermalprinter.ConnectBluetoothActivity
import com.application.isradeleon.thermalprinter.models.PrintAlignment
import com.application.isradeleon.thermalprinter.models.PrintFont
import com.application.isradeleon.thermalprinter.models.ThermalPrinter
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
import com.projectassyifa.syifamilk.data.transaction.model.OrderModel
import com.projectassyifa.syifamilk.data.transaction.model.OrderPrint
import com.projectassyifa.syifamilk.data.transaction.vm.TransactionVM
import com.projectassyifa.syifamilk.screen.home.HomeActivity
import com.projectassyifa.syifamilk.screen.oder.OrderActivity
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_payment.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class PaymentActivity : AppCompatActivity() {

    var pay_method : String? = null
    var pay_method_name : String? = null
    var id_transaksi : String? =null

    var listProduct :ArrayList<String>? = ArrayList()

    lateinit var adapterCart: AdapterCart
    var rpkembali :Int? = 0
    val localeID = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

    var total = 0
    var total_pay : Int = 0
    var pay : Int = 0


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
            subtotal1.setText(formatRupiah.format(it.total_payment))
            total = it.total_payment


//            total_pay = it.total_payment.toString().toInt()
//            pay = it.total_payment.toString().toInt()
            id_transaksi = it.id_transactions
            id_trans.text = it.id_transactions
        })
        transactionVM.get_trans_id(this,intent.getStringExtra("id_transaksi").toString())

        //order list
        order_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        transactionVM.data_order?.observe(this, Observer {
            adapterOrder = AdapterOrder(it,this)
            order_list.adapter = adapterOrder

            println("PRODUCT NAME ${it[0].product_name}")

            it.forEach {
                listProduct?.add("${it.product_name}  ${it.product_qty}x  ${it.total_price_item}\n" )
                println("PRODUK NICH $listProduct")
            }
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
                    pay_method_name = adapterPaymentMethod.list_pmethod[position].name_payment_method

                }

            })
        })

        paymentVM.get_payment_method(this)

        val username = dataLogin?.getString(
            getString(R.string.username),
            getString(R.string.default_value)
        )

     pay_bayar.addTextChangedListener(object : TextWatcher{
         override fun beforeTextChanged(t: CharSequence?, p1: Int, p2: Int, p3: Int) {

         }

         override fun onTextChanged(t: CharSequence?, p1: Int, p2: Int, p3: Int) {
//             kembaliantv.setText("Text in EditText : "+t)
             if (isPosOrNegNumber(t.toString()) == true) {
                  rpkembali = (pay_bayar.value.toInt() - total)
                 kembaliantv.setText(formatRupiah.format(rpkembali))
             } else {
                 pay_bayar.setText("0")
                  rpkembali = (pay_bayar.value.toInt() - total)
                 println("PAY1 ${pay_bayar.value}")
                 kembaliantv.setText(formatRupiah.format(rpkembali))
             }

         }

         override fun afterTextChanged(t: Editable?) {

         }

     })


        btn_payment_proses.setOnClickListener {

            if (pay_method == null){
                Toast.makeText(
                    this,
                    "Piih opsi pembayaran!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                var data_payment = PaymentModel()
                data_payment.id_transaction = id_trans.text.toString()
                data_payment.payment_method = pay_method.toString()
                data_payment.customer_id = pay_idcustomer.text.toString()
                data_payment.customer_name = pay_namecustomer.text.toString()
                if (rpkembali!!.toInt() < 0){
                    data_payment.total_payment = pay_bayar.value.toInt()
                    data_payment.remaining = rpkembali!!
                } else {
                    data_payment.total_payment = (pay_bayar.value.toInt() - rpkembali!!)
                    data_payment.remaining = 0
                }

                data_payment.created_by = username.toString()


//            println("DATA PAYMENT ID : ${data_payment.id_transaction}")
//            println("DATA PAYMENT ID CUST :  ${data_payment.customer_id}")
//            println("DATA PAYMENT NAMA CUST :  ${data_payment.customer_name}")
//            println("DATA PAYMENT METHOD PAY :  ${data_payment.payment_method}")
//            println("DATA PAYMENT TOTAL PAY :  ${data_payment.total_payment}")
//            println("DATA PAYMENT CREATED BY  :  ${data_payment.created_by}")
//                println("PAY VALUE :  ${pay_bayar.value}")
//                println("TOTAL_PAY  :  ${data_payment.total_payment}")
//                println("REMAINING  :  ${data_payment.remaining}")

                paymentVM.responseAPI?.observe(this, Observer {
                    if (it.status){
                        finish()
                        startActivity(Intent(this,OrderActivity::class.java))
                    }
                })
                paymentVM.post_payment(this,data_payment)
            }
        }


        //PRINT STRUK
        btn_print.setOnClickListener {
            if (pay_method == null) {
                Toast.makeText(
                    this,
                    "Piih opsi pembayaran!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startActivityForResult(
                    Intent(this, ConnectBluetoothActivity::class.java),
                    ConnectBluetoothActivity.CONNECT_BLUETOOTH
                )
            }
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val nama_kasir = dataLogin?.getString(
            getString(R.string.nama_pegawai),
            getString(R.string.default_value)
        )
//        var dataName : ArrayList<String> = ArrayList()
//        var dataQty : ArrayList<String> = ArrayList()
//        var dataItemPrice : ArrayList<String> = ArrayList()
//        listProduct.forEach {
//            dataName.add(it.product_name)
//            dataQty.add(it.product_qty)
//            dataItemPrice.add(it.total_price_item)
//        }

        var produk = listProduct?.joinToString("")
        println("PRODUKS ANJAY $produk")
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val timeNow = sdf.format(Date())
        if(resultCode == Activity.RESULT_OK && requestCode == ConnectBluetoothActivity.CONNECT_BLUETOOTH){
            // ThermalPrinter is ready
            ThermalPrinter.instance

                .write("Syifamilk & Coffe", PrintAlignment.CENTER, PrintFont.NORMAL)
                .writeImage(BitmapFactory.decodeResource(getResources(), R.drawable.logo_syifamilk1))
                .write("No. Transactions : $id_transaksi",PrintAlignment.LEFT,PrintFont.NORMAL)
                .write("Pelanggan        : ${pay_namecustomer.text}",PrintAlignment.LEFT,PrintFont.NORMAL)
//                .write("--------------------------------",PrintAlignment.CENTER)
//                .write("Produk        Jumlah        Harga",PrintAlignment.CENTER)
                .write("--------------------------------",PrintAlignment.CENTER)
                .write("$produk", PrintAlignment.LEFT)
                .write("--------------------------------",PrintAlignment.CENTER)
                .write("Total Belanja : ${subtotal1.text}  ", PrintAlignment.CENTER, PrintFont.NORMAL)
                .write("--------------------------------",PrintAlignment.CENTER)
                .write("Kasir  : $nama_kasir  ", PrintAlignment.LEFT, PrintFont.NORMAL)
                .write("Waktu  : $timeNow  ", PrintAlignment.LEFT, PrintFont.NORMAL)
                .write("Metode : $pay_method_name ", PrintAlignment.LEFT, PrintFont.NORMAL)
                .write("--------------------------------",PrintAlignment.CENTER)
                .write("Terimakasih", PrintAlignment.CENTER, PrintFont.NORMAL)
                .write("")

//                .writeImage(BitmapFactory.decodeResource(getResources(), R.drawable.lg_kawarung))
                .print()

        }
    }

    fun isPosOrNegNumber(s: String?) : Boolean {
        val regex = """^(-)?[0-9]{0,}((\.){1}[0-9]{1,}){0,1}$""".toRegex()
        return if (s.isNullOrEmpty()) false
        else regex.matches(s)

    }
}