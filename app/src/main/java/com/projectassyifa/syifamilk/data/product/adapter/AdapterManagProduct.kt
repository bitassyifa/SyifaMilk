package com.projectassyifa.syifamilk.data.product.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.product.model.ProductModel
import com.projectassyifa.syifamilk.data.product.vm.ProductVM
import com.projectassyifa.syifamilk.data.user.adapter.UserVH
import com.projectassyifa.syifamilk.screen.payment.PaymentActivity
import com.projectassyifa.syifamilk.screen.product.AddProductLayout
import com.projectassyifa.syifamilk.screen.product.ManagProductActivity
import com.projectassyifa.syifamilk.screen.product.UpdateProductLayout
import com.projectassyifa.syifamilk.screen.user.ManagementUser
import com.projectassyifa.syifamilk.screen.user.UpdateUserLayout
import javax.inject.Inject

class AdapterManagProduct(var list_product : List<ProductModel>, var activity: Activity):RecyclerView.Adapter<ProductVH1>() {

    private val aktivitas : ManagProductActivity = activity as ManagProductActivity
    @Inject
    lateinit var productVM: ProductVM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH1 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_manag_product,parent,false)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)
        return ProductVH1(view)
    }

    override fun onBindViewHolder(holder: ProductVH1, position: Int) {
        var daftar = list_product[position]
        holder.id.text = daftar.product_id.toString()
        holder.nama.text = daftar.product_name
        holder.harga.text = daftar.product_price.toString()
        holder.stok.text = daftar.product_stock.toString()

        holder.edit.setOnClickListener {
            // Initialize a new instance of
            val builder = AlertDialog.Builder(activity)

            // Set the alert dialog title
            builder.setTitle("Konfirmasi Update User")

            // Display a message on alert dialog
            builder.setMessage("Update data ${list_product[position].product_name}?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Ya") { dialog, which ->
                // Do something when user press the positive button
                var dialog = UpdateProductLayout()
                val bundle = Bundle()
                dialog.arguments = bundle

                bundle.putString("product_id", list_product[position].product_id)
                bundle.putString("product_name", list_product[position].product_name)
                bundle.putString("product_description", list_product[position].product_description)
                bundle.putString("product_price", list_product[position].product_price.toString())
                bundle.putString("product_stock", list_product[position].product_stock.toString())
                bundle.putString("product_image", list_product[position].product_image)
                bundle.putString("product_category", list_product[position].product_category)

                dialog.show(aktivitas.supportFragmentManager, "customDialog")
            }


            // Display a negative button on alert dialog
            builder.setNegativeButton("Tidak"){dialog,which ->
                //                    Toast.makeText(activity,"Anda tidak yakin.", Toast.LENGTH_SHORT).show()
            }


            // Display a neutral button on alert dialog
            //                        builder.setNeutralButton("Cancel"){_,_ ->
            //                            Toast.makeText(activity,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
            //                        }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
            }


            holder.delete.setOnClickListener {
                // Initialize a new instance of
                val builder = AlertDialog.Builder(activity)

                // Set the alert dialog title
                builder.setTitle("Konfirmasi Delete Produk")

                // Display a message on alert dialog
                builder.setMessage("Hapus data ${list_product[position].product_name}?")

                // Set a positive button and its click listener on alert dialog
                builder.setPositiveButton("Ya") { dialog, which ->
                    // Do something when user press the positive button
//                productVM.response_product?.observe(aktivitas, Observer {
//                    if (it.status){
//                        aktivitas.finishMe()
//                    }
//                })
                    productVM.delete_product(list_product[position].product_id, activity)
                    aktivitas.finishMe()
                }


                // Display a negative button on alert dialog
                builder.setNegativeButton("Tidak") { dialog, which ->
                    //                    Toast.makeText(activity,"Anda tidak yakin.", Toast.LENGTH_SHORT).show()
                }


                // Display a neutral button on alert dialog
                //                        builder.setNeutralButton("Cancel"){_,_ ->
                //                            Toast.makeText(activity,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
                //                        }

                // Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()

                // Display the alert dialog on app interface
                dialog.show()
            }


        }


    override fun getItemCount(): Int {
        return list_product.count()
    }

}

class ProductVH1(view: View) : RecyclerView.ViewHolder(view) {
    var id = view.findViewById<TextView>(R.id.id_product)
    var nama = view.findViewById<TextView>(R.id.nama_produk)
    var harga = view.findViewById<TextView>(R.id.harga_produk)
    var stok = view.findViewById<TextView>(R.id.stok)
    var edit = view.findViewById<ImageButton>(R.id.btn_edit_produk)
    var delete = view.findViewById<ImageButton>(R.id.btn_delete_product)

}
