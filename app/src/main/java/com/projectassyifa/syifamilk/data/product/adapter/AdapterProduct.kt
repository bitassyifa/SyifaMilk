package com.projectassyifa.syifamilk.data.product.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.data.cart.vm.CartVM
import com.projectassyifa.syifamilk.data.product.model.ProductModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.text.Typography.times


class AdapterProduct (val  listProduct: List<ProductModel>, var activity: Activity, var cartVM: CartVM)
    :RecyclerView.Adapter<ProductVH>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_list_product,parent,false)
//        (activity?.applicationContext as MyApp).applicationComponent.inject(this)
        return ProductVH(view)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.nama_product.text = listProduct[position].product_name
        holder.harga_product.text = listProduct[position].product_price.toString()
//        holder.stock.text ="Stock : ${listProduct[position].product_stock}"

        var linkfoto = "http://202.62.9.138/syifamilk_api/uploads/foto_product/${listProduct[position].product_image}"
        Glide
            .with(activity)
            .load(linkfoto)
            .fitCenter()
            .placeholder(R.drawable.we)
            .into(holder.product_image);



        var quantity = 1
        holder.productQty.text= quantity.toString()
        holder.increment.setOnClickListener {
            quantity++
            holder.productQty.text= quantity.toString()
        }

        holder.decrement.setOnClickListener {
            if (quantity ==1){
                println("minimal 1 pembelian")
                Toast.makeText(
                    activity,
                    "minimal 1 pembelian",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                quantity--
                holder.productQty.text = quantity.toString()
            }
        }
        holder.btn_order.setOnClickListener {
//        2.times(4)
            var harga : Int = listProduct[position].product_price.toInt()
            var qty : Int = holder.productQty.text.toString().toInt()
            var total_harga : Int = harga.times(qty)
            cartVM.addCartList(listProduct[position].product_id,listProduct[position].product_name,total_harga,qty,listProduct[position].product_image,listProduct[position].product_price)

        }


    }

    override fun getItemCount(): Int {
      return listProduct.size
    }

    }


//}

class ProductVH  (view: View) : RecyclerView.ViewHolder(view){
    var nama_product = view.findViewById<TextView>(R.id.nama_product)
    var harga_product = view.findViewById<TextView>(R.id.harga_product)
//    var stock = view.findViewById<TextView>(R.id.stok1)
//    var desc_product = view.findViewById<TextView>(R.id.desc_product)
    var product_image = view.findViewById<CircleImageView>(R.id.product_image)
    var increment = view.findViewById<ImageButton>(R.id.buttonPlus)
    var decrement = view.findViewById<ImageButton>(R.id.buttonMinus)
    var productQty = view.findViewById<TextView>(R.id.qty)
    var btn_order = view.findViewById<Button>(R.id.order)

}
