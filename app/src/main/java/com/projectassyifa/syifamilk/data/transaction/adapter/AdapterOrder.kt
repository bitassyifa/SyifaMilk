package com.projectassyifa.syifamilk.data.transaction.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.transaction.model.OrderModel

class AdapterOrder (var list_order : List<OrderModel>,var activity: Activity) :RecyclerView.Adapter<OrderVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderVH {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_order,parent,false)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)
        return OrderVH(view)
    }

    override fun onBindViewHolder(holder: OrderVH, position: Int) {
        var daftar = list_order[position]
        holder.nama_produk.text = daftar.product_name
        holder.qty_produk.text = daftar.product_qty
        holder.harga_produk.text = daftar.total_price_item
    }

    override fun getItemCount(): Int {
        return list_order.count()
    }
}

class OrderVH (view: View) : RecyclerView.ViewHolder(view){
    var nama_produk = view.findViewById<TextView>(R.id.order_product)
    var qty_produk = view.findViewById<TextView>(R.id.order_qty)
    var harga_produk = view.findViewById<TextView>(R.id.order_price)


}
