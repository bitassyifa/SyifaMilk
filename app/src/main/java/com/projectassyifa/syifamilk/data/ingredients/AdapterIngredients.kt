package com.projectassyifa.syifamilk.data.ingredients

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.screen.ingredients.IngredientsActivity
import com.projectassyifa.syifamilk.screen.ingredients.UpdateIngredients
import com.projectassyifa.syifamilk.screen.user.ManagementUser
import com.projectassyifa.syifamilk.screen.user.UpdateUserLayout
import javax.inject.Inject

class AdapterIngredients (var listIngredients : List<IngredientsModel>,var activity: Activity) : RecyclerView.Adapter<IngredientsVH>(){

    @Inject
    lateinit var ingredientsVM: IngredientsVM
    private val aktivitas : IngredientsActivity = activity as IngredientsActivity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsVH {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_ingredients,parent,false)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)
        return IngredientsVH(view)
    }

    override fun onBindViewHolder(holder: IngredientsVH, position: Int) {
       var daftar = listIngredients[position]
        holder.no.text = (position + 1).toString()
        holder.id.text = daftar.id_ingredients
        holder.name.text = daftar.name_ingredients
        holder.qty.text = daftar.qty
        holder.total.text = daftar.total
        holder.price.text =daftar.price.toString()

        holder.delete.setOnClickListener {

            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Konfirmasi Delete Bahan Pokok")
            builder.setMessage("Hapus data ${listIngredients[position].name_ingredients}?")
            builder.setPositiveButton("Ya") { dialog, which ->
                ingredientsVM.deleteIngredients(activity,listIngredients[position].id_ingredients,)
                aktivitas.finishMe()
            }
            builder.setNegativeButton("Tidak"){dialog,which ->
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        holder.edit.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Konfirmasi Edit Bahan Pokok")
            builder.setMessage("Edit data ${listIngredients[position].name_ingredients}?")
            builder.setPositiveButton("Ya") { dialog, which ->
                var dialog = UpdateIngredients()
                val bundle= Bundle()
                dialog.arguments = bundle

                bundle.putString("id_ingredients",listIngredients[position].id_ingredients)
                bundle.putString("name_ingredients",listIngredients[position].name_ingredients)
                bundle.putString("qty",listIngredients[position].qty)
                bundle.putString("price",listIngredients[position].price.toString())
                dialog.show(aktivitas.supportFragmentManager, "customDialog")
            }
            builder.setNegativeButton("Tidak"){dialog,which ->
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }

    override fun getItemCount(): Int {
        return listIngredients.size
    }

}

class IngredientsVH (view: View) : RecyclerView.ViewHolder(view) {
    var no = view.findViewById<TextView>(R.id.no_ingre)
    var id = view.findViewById<TextView>(R.id.id_ingre)
    var name = view.findViewById<TextView>(R.id.name_ingre)
    var qty = view.findViewById<TextView>(R.id.qty_ingre)
    var price = view.findViewById<TextView>(R.id.price_ingre)
    var total = view.findViewById<TextView>(R.id.total_ingre)
    var delete = view.findViewById<ImageButton>(R.id.btn_delete_ingre)
    var edit = view.findViewById<ImageButton>(R.id.btn_edit_ingre)
}
