package com.projectassyifa.syifamilk.screen.dashboard

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.CircularGauge
import com.anychart.charts.Pie
import com.bumptech.glide.Glide
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.report.vm.ReportVM
import com.projectassyifa.syifamilk.screen.alert.Diagram
import com.projectassyifa.syifamilk.screen.alert.Successfully
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class DashboardActivity : AppCompatActivity() {



    @Inject
    lateinit var reportVM : ReportVM
    val localeID = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    private var chart : AnyChartView? = null
    private var product : ArrayList<String> = ArrayList()
   private  var jumlah : ArrayList<Int> = ArrayList()
    private val salary = listOf(200,300,400,600)
    private  val month  = listOf("January","Februari","Maret","April")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        (applicationContext as MyApp).applicationComponent.inject(this)

        val animate = Diagram(this)
        animate.startAnimate()
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                animate.isDismiss()
            }

        }, 2000)

        //CHART PRODUCT
        chart = findViewById(R.id.chart_sale_product)
        reportVM.data_report_product?.observe(this, Observer {
            val pie : Pie = AnyChart.pie()
            val dataPieChart : MutableList<DataEntry> = mutableListOf()

            it.forEach {
                product?.add(it.product_name)
                jumlah?.add(it.jumlah)
            }


            println("DATA PRODUCT $product")
            for ( index in jumlah.indices){
                dataPieChart.add(ValueDataEntry(product.elementAt(index),jumlah.elementAt(index)))
            }
            pie.data(dataPieChart)
            pie.title("Produk Terlaris Bulan Ini ")
            chart!!.setChart(pie)

        })
        reportVM.get_sale_product(this)


        //Report Income
        reportVM.data_report_income?.observe(this, Observer {


//            subtotal.setText(formatRupiah.format(totalPrice))

            income_perhari.setText(formatRupiah.format(it.perhari))
            income_perbulan.setText(formatRupiah.format(it.perminggu))
            income_perminggu.setText(formatRupiah.format(it.perbulan))
            income_pertahun.setText(formatRupiah.format(it.pertahun))
        })
        reportVM.get_income(this)
//        configChartView()

        reportVM.data_report_balance?.observe(this, Observer {
                income_payment.setText(formatRupiah.format(it.income_payment))
                income_transaksi.setText(formatRupiah.format(it.income_transaksi))
            if (it.income_payment == it.income_transaksi){
                Glide.with(this)
                    .load(this.getResources().getDrawable(R.drawable.balance))
                    .into(img_balance);
            } else {
                Glide.with(this)
                    .load(this.getResources().getDrawable(R.drawable.notbalance))
                    .into(img_balance);
            }
        })
        reportVM.get_income_balance(this)

    }

    private fun configChartView() {
//        val pie : Pie = AnyChart.pie()
//        val dataPieChart : MutableList<DataEntry> = mutableListOf()
//
//        println("List PRODUCT $product1")
//        println("List JUMLAH $jumlah1")
//        for ( index in jumlah1.indices){
//            dataPieChart.add(ValueDataEntry(product1.elementAt(index),jumlah1.elementAt(index)))
//        }
//        pie.data(dataPieChart)
//        pie.title("Product Terlaris Bulan Ini ")
//        chart!!.setChart(pie)

    }
}