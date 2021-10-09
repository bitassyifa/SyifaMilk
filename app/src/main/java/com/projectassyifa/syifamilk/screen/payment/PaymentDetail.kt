package com.projectassyifa.syifamilk.screen.payment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.projectassyifa.syifamilk.R
import kotlinx.android.synthetic.main.fragment_payment_detail.*


class PaymentDetail : DialogFragment(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments

        val id_payment = bundle!!.getString("id_payment")
        val total_payment = bundle!!.getString("total_payment")
        val tanggal = bundle!!.getString("tanggal")
        val customer_name = bundle!!.getString("customer_name")
        val id_transactions = bundle!!.getString("id_transactions")
        val remaining = bundle!!.getString("remaining")
        val name_method_payment = bundle!!.getString("name_payment_method")
        val logo = bundle!!.getString("logo")
        val status = bundle!!.getString("status")

        payment_id.text =id_payment
        custname.text = ": $customer_name"
        total_amount.text = ": $total_payment"
        id_trans2.text = ": $id_transactions"
        stts.text = ": $status"
        sisa.text = ": $remaining"
        tgl.text = ": $tanggal"
        method_pay.text = ": $name_method_payment"

        var linkfoto = "http://202.62.9.138/syifamilk_api/uploads/logo_method_payment/$logo"
        Glide
            .with(this)
            .load(linkfoto)
            .fitCenter()
            .placeholder(R.drawable.we)
            .into(logo_pay);
//println("MULAI")
//        for (i in 10 until 12) {
//            print("$i ")
//        }
//
//        val x = 11
//        when (x) {
//            10, 11 -> print("a")
//            11, 12 -> print("b")
//        }
//
//        for (i in 1..3) {
//            for (j in 1..i) {
//                print(j)
//            }
//        }

    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.60).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        getDialog()!!.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
    }
}