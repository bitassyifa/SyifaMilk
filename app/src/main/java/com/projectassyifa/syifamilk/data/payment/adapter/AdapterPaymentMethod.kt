package com.projectassyifa.syifamilk.data.payment.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.payment.model.PaymentMethodModel
import com.projectassyifa.syifamilk.data.product.adapter.ProductVH
import org.w3c.dom.Text

class AdapterPaymentMethod (val list_pmethod : List<PaymentMethodModel>,var activity: Activity):RecyclerView.Adapter<PMethodVH>(){


    var onClick: OnItemClickListener? = null
    private var selected = -1

    fun setSelection(position: Int) {
        selected = position
        notifyDataSetChanged()
    }

    fun setOnItemClickLitener(mOnItemClickListener: OnItemClickListener) {
        this.onClick = mOnItemClickListener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PMethodVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_method_payment,parent,false)

//        (activity?.applicationContext as MyApp).applicationComponent.inject(this)
        return PMethodVH(view)
    }

    override fun onBindViewHolder(holder: PMethodVH, position: Int) {
        holder.nama_pay.text = list_pmethod[position].name_payment_method

        var linkfoto = "http://202.62.9.138/syifamilk_api/uploads/logo_method_payment/${list_pmethod[position].logo}"
        Glide
            .with(activity)
            .load(linkfoto)
            .fitCenter()
            .placeholder(R.drawable.we)
            .into(holder.logo_pay);

        if (selected == position) {
            println("${list_pmethod[position].id_payment_method}")
            holder.check.isChecked = true
            holder.itemView.isSelected = true
        } else {
            holder.check.isChecked = false
            holder.itemView.isSelected = false
        }
        if (onClick != null) {
            holder.wadah.setOnClickListener {
                onClick!!.onItemClick(
                    holder.itemView,
                    holder.adapterPosition
                )
            }
        }


    }

    override fun getItemCount(): Int {
        return list_pmethod.size
    }
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}


class PMethodVH (itemView: View) : RecyclerView.ViewHolder(itemView){
    var nama_pay = itemView.findViewById<TextView>(R.id.name_pay)
    var logo_pay = itemView.findViewById<ImageView>(R.id.logo_pay)
    var wadah = itemView.findViewById<LinearLayout>(R.id.wadah_pay)
    var check = itemView.findViewById<CheckBox>(R.id.checkbox)

}
