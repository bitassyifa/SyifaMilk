package com.projectassyifa.syifamilk.data.cart.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import javax.inject.Inject

class AdapterCart(
    private val cartList :MutableList<CartModel>,
    val cartVM: CartVM ,var subtotal : TextView,var btnPayment : Button,var activity :Activity) :RecyclerView.Adapter<CartVH>() {
    @Inject
    lateinit var transactionVM : TransactionVM
    private val aktivitas : OrderActivity = activity as OrderActivity
    var qty : Int? = 0



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_cart,parent,false)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)

//        mContext = parent.context
//        mLifecycleOwner = mContext as LifecycleOwner

        return CartVH(view)
    }

    override fun onBindViewHolder(holder: CartVH, position: Int) {
//        qty = cartList[position].productQty
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
//        holder.decrement.isClickable = holder.productQty.text.toString().toInt() > 1
//        holder.increment.setOnClickListener {
//            println("KLIK +")
//
//            holder.productQty.text = (holder.productQty.text.toString().toInt() +1).toString()
//            holder.productPrice.text = cartList[position].productPrice.times(holder.productQty.text.toString().toInt()).toString()
//            holder.decrement.isClickable = holder.productQty.text.toString().toInt() > 1
//
//            var totalPrice = 0
//            for (i in cartVM.cartlist.indices){
//                totalPrice += (cartVM.cartlist[i].productPrice.toInt())
//            }
//            subtotal.text = "Rp. $totalPrice"
//
//        }
//        holder.decrement.setOnClickListener {
//            println("KLIK -")
//            holder.productQty.text = (holder.productQty.text.toString().toInt() -1).toString()
//            holder.productPrice.text = cartList[position].productPrice.times(holder.productQty.text.toString().toInt()).toString()
//            holder.decrement.isClickable = holder.productQty.text.toString().toInt() > 1
//
//        }

        //total price
        var totalPrice = 0
        for (i in cartVM.cartlist.indices){
            totalPrice += (cartVM.cartlist[i].productPrice.toInt())
        }
        subtotal.text = "Rp. $totalPrice"

        //data order
        var tampungID : ArrayList<String> = ArrayList()
        var tampungName : ArrayList<String> = ArrayList()
        var tampungPrice : ArrayList<String> = ArrayList()
        var tampungQty : ArrayList<String> = ArrayList()
        var tampungAll : ArrayList<String> = ArrayList()



        btnPayment.setOnClickListener {
            var tampung_data_product : ArrayList<DataProduct> = ArrayList()
            cartList.forEach {

//                println("DATA id ${it.productID}")
//                println("DATA nama ${it.productName}")
//                println("DATA harga ${it.productPrice}")
//                println("DATA qty ${it.productQty}")
                val data_product = DataProduct(
                    product_id = it.productID,
                    product_qty = it.productQty,
                    item_price = it.unitPrice.toInt()
                )
                tampung_data_product.add(data_product)
            }

            val data_order = TransactionModel_post(
                created_by = "apinchocs",
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



//                val intent = Intent(activity, PaymentActivity::class.java)
//                intent.putExtra("subtotal",totalPrice.toString())
//                intent.putStringArrayListExtra("order",tampungName)
//            intent.putStringArrayListExtra("id",tampungID)
//            intent.putStringArrayListExtra("price",tampungPrice)
//            intent.putStringArrayListExtra("qty",tampungQty)
//               activity.startActivity(intent)
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

}
