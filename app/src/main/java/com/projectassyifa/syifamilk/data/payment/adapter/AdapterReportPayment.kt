package com.projectassyifa.syifamilk.data.payment.adapter

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.payment.model.PaymentReportModel
import com.projectassyifa.syifamilk.data.transaction.adapter.TransVH
import com.projectassyifa.syifamilk.screen.oder.OrderDetail
import com.projectassyifa.syifamilk.screen.payment.PaymentDetail

class AdapterReportPayment (var list_payment : List<PaymentReportModel>,var activity: Activity) : RecyclerView.Adapter<PaymentVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_report_payment,parent,false)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)
        return PaymentVH(view)
    }

    override fun onBindViewHolder(holder: PaymentVH, position: Int) {
       var daftar = list_payment[position]

        if (daftar.status == "LUNAS"){
            holder.status.setBackgroundResource(R.drawable.bg_paid)
            holder.status.setTextColor(Color.WHITE)
        }
        if (daftar.status == "BELUM LUNAS"){
            holder.status.setBackgroundResource(R.drawable.bg_unpaid)
            holder.status.setTextColor(Color.WHITE)
        }
        holder.no.text = (position+1).toString()
        holder.id.text = daftar.id_payment
        holder.total.text = daftar.total_payment
        holder.tanggal.text = daftar.tanggal
        holder.status.text = daftar.status
        holder.remaining.text = daftar.remaining
        val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager
        holder.detail.setOnClickListener {
            var dialog = PaymentDetail()
            val bundle= Bundle()
            dialog.arguments = bundle

            bundle.putString("id_payment",list_payment[position].id_payment)
            bundle.putString("id_transactions",list_payment[position].id_transactions)
            bundle.putString("total_payment",list_payment[position].total_payment)
            bundle.putString("tanggal",list_payment[position].tanggal)
            bundle.putString("status",list_payment[position].status)
            bundle.putString("customer_name",list_payment[position].customer_name)
            bundle.putString("name_payment_method",list_payment[position].name_payment_method)
            bundle.putString("remaining",list_payment[position].remaining)
            bundle.putString("logo",list_payment[position].logo)

            dialog.show(manager, "customDialog")
        }
    }

    override fun getItemCount(): Int {
    return list_payment.size
    }
}

class PaymentVH (view: View) : RecyclerView.ViewHolder(view){
    var id = view.findViewById<TextView>(R.id.id_payment)
    var total = view.findViewById<TextView>(R.id.total_payment)
    var tanggal = view.findViewById<TextView>(R.id.tgl_payment)
    var status = view.findViewById<TextView>(R.id.sts_payment)
    var detail = view.findViewById<ImageView>(R.id.detail_pay)
    var remaining = view.findViewById<TextView>(R.id.remain)
    var no = view.findViewById<TextView>(R.id.no_payment)
}
