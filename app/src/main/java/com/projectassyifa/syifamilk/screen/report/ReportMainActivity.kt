package com.projectassyifa.syifamilk.screen.report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.projectassyifa.syifamilk.R

class ReportMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_main)
        var viewPager = findViewById(R.id.viewPager) as ViewPager
        var tablayout = findViewById(R.id.tablayout) as TabLayout
        val fragmentAdapter = AdapterScreenReport(supportFragmentManager)
        fragmentAdapter.addFragment(ReportTransactions(),"Transaksi")
        fragmentAdapter.addFragment(ReportPayment(),"Pembayaran")
        viewPager.adapter = fragmentAdapter
        tablayout.setupWithViewPager(viewPager)
    }
}