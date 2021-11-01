package com.projectassyifa.syifamilk.screen.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdapterScreenReport(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {

                val bundle = Bundle()
                bundle.putString("fragmentName", "Transaksi")
                val transasctionFr = ReportTransactions()
                transasctionFr.arguments = bundle
                return transasctionFr
            }
            1 -> {

                val bundle = Bundle()
                bundle.putString("fragmentName", "Pembayaran")
                val paymentFm = ReportPayment()
                paymentFm.arguments = bundle
                return paymentFm
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Product")
                val productFm = ReportProductSale()
                productFm.arguments = bundle
                return productFm
            }
            else -> return ReportTransactions()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}