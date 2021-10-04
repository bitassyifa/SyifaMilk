package com.projectassyifa.syifamilk.data.transaction.adapter

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.transaction.model.TransactionModel
import com.projectassyifa.syifamilk.screen.oder.OrderDetail
import com.projectassyifa.syifamilk.screen.report.ReportTransactions
import com.projectassyifa.syifamilk.screen.user.ManagementUser
import com.projectassyifa.syifamilk.screen.user.UpdateUserLayout

class AdapterReportTrans(var list_trans : List<TransactionModel>,var activity: Activity) : RecyclerView.Adapter<TransVH>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_report_transaction,parent,false)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)
        return TransVH(view)
    }

    override fun onBindViewHolder(holder: TransVH, position: Int) {
       var daftar = list_trans[position]

        holder.id.text = daftar.id_transactions
        holder.total.text =  daftar.total_payment
        holder.status.text = daftar.status
        holder.tanggal.text = daftar.tanggal

        if (daftar.status == "paid"){
            holder.status.setBackgroundResource(R.drawable.bg_paid)
            holder.status.setTextColor(Color.WHITE)
        }
        if (daftar.status == "unpaid"){
            holder.status.setBackgroundResource(R.drawable.bg_unpaid)
            holder.status.setTextColor(Color.WHITE)
        }
        val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager
        holder.detail.setOnClickListener {
            var dialog = OrderDetail()
            val bundle= Bundle()
            dialog.arguments = bundle

            bundle.putString("id_trans",list_trans[position].id_transactions)

            dialog.show(manager, "customDialog")
        }


    }

    override fun getItemCount(): Int {
        return list_trans.count()
    }
}

class TransVH (view: View) : RecyclerView.ViewHolder(view) {

    var id = view.findViewById<TextView>(R.id.id_trans)
    var total = view.findViewById<TextView>(R.id.total_trans)
    var status = view.findViewById<TextView>(R.id.status_trans)
    var tanggal = view.findViewById<TextView>(R.id.tgl_trans)
    var detail = view.findViewById<ImageView>(R.id.btn_detail_trans)

}
