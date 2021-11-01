package com.projectassyifa.syifamilk.data.report.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.product.model.ProductModel
import com.projectassyifa.syifamilk.data.report.model.ReportProductModel

class AdapterReportProductDay (var list_prod : List<ReportProductModel>,var activity: Activity) : RecyclerView.Adapter<ProductSaleVH>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSaleVH {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_productsale,parent,false)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)
        return ProductSaleVH(view)
    }

    override fun onBindViewHolder(holder: ProductSaleVH, position: Int) {
       var daftar = list_prod[position]
        holder.no.text = (position + 1).toString()
        holder.id.text = daftar.product_id
        holder.nameproduct.text = daftar.product_name
        holder.jml.text = daftar.jumlah.toString()
    }

    override fun getItemCount(): Int {
        return list_prod.size
    }
}

class ProductSaleVH (view: View) : RecyclerView.ViewHolder(view) {
    var no = view.findViewById<TextView>(R.id.no_prod)
    var id = view.findViewById<TextView>(R.id.id_prod)
    var nameproduct = view.findViewById<TextView>(R.id.name_prod)
    var jml = view.findViewById<TextView>(R.id.jumlah_prod)
}
