package com.projectassyifa.syifamilk.data.cart.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.cart.model.CartModel
import com.projectassyifa.syifamilk.data.cart.vm.CartVM
import com.projectassyifa.syifamilk.data.transaction.model.DataProduct
import com.projectassyifa.syifamilk.data.transaction.model.TransactionModel_post
import com.projectassyifa.syifamilk.data.transaction.vm.TransactionVM
import com.projectassyifa.syifamilk.screen.oder.OrderActivity
import com.projectassyifa.syifamilk.screen.payment.PaymentActivity
import com.projectassyifa.syifamilk.screen.user.ManagementUser
import de.hdodenhof.circleimageview.CircleImageView
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class AdapterCart(
    private val cartList :MutableList<CartModel>,
    val cartVM: CartVM ,var subtotal : TextView,var btnPayment : Button,var activity :Activity,var username : String) :RecyclerView.Adapter<CartVH>() {

    @Inject
    lateinit var transactionVM : TransactionVM

    private val aktivitas : OrderActivity = activity as OrderActivity
    var qty : Int? = 0




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_cart,parent,false)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)

        return CartVH(view)
    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {

        holder.productName.text = cartList[position].productName
        holder.productPrice.text = cartList[position].productPrice.toString()
        holder.productQty.text= cartList[position].productQty.toString()
        var linkfoto = "http://202.62.9.138/syifamilk_api/uploads/foto_product/${cartList[position].productImage}"
        Glide
            .with(activity)
            .load(linkfoto)
            .fitCenter()
            .placeholder(R.drawable.we)
            .into(holder.product_image);
        holder.unitPrice.text= cartList[position].unitPrice


        //total price
        var totalPrice = 0
        for (i in cartVM.cartlist.indices){
            totalPrice += (cartVM.cartlist[i].productPrice.toInt())
        }

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        subtotal.setText(formatRupiah.format(totalPrice))

//        subtotal.text = "Rp. $totalPrice"

      //delete cart
        holder.del_cart.setOnClickListener {
           cartVM.deleteCartList(position)
        }


        btnPayment.setOnClickListener {

            if (cartList != null){
                var tampung_data_product : ArrayList<DataProduct> = ArrayList()
                cartList.forEach {

                    val data_product = DataProduct(
                        product_id = it.productID,
                        product_qty = it.productQty,
                        item_price = it.unitPrice.toInt()
                    )
                    tampung_data_product.add(data_product)
                }

                val data_order = TransactionModel_post(
                    created_by = username,
                    data_product = tampung_data_product
                )
                println("DATA PRODUCT ${data_order.created_by},${data_order.data_product}")


                transactionVM.responseAPI?.observe(aktivitas, Observer {
                    if (it.status){
                        println("ID TRANSACTIONS ${it.data}")
                        val id_transaksi = it.data.toString()
                        val intent = Intent(activity, PaymentActivity::class.java)
                        intent.putExtra("id_transaksi",id_transaksi)
                        activity.startActivity(intent)
                    }
                })
                transactionVM.add_transaction(activity,data_order)
            } else {
                println("minimal 1 pembelian")
            }



        }

    }

    override fun getItemCount(): Int {
       return cartList.size
    }
}

class CartVH (v:View): RecyclerView.ViewHolder(v) {
    var productName = v.findViewById<TextView>(R.id.nameproduct)
    var productPrice = v.findViewById<TextView>(R.id.priceproduct)
    var productQty = v.findViewById<TextView>(R.id.qtyproduct)
    var product_image = v.findViewById<CircleImageView>(R.id.product_image1)
    var unitPrice = v.findViewById<TextView>(R.id.unitprice)
    var del_cart = v.findViewById<ImageButton>(R.id.del_cart)

}
