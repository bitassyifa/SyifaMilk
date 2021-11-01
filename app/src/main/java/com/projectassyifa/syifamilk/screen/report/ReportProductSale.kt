package com.projectassyifa.syifamilk.screen.report

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.report.adapter.AdapterReportProductDay
import com.projectassyifa.syifamilk.data.report.vm.ReportVM
import kotlinx.android.synthetic.main.fragment_report_payment.*
import kotlinx.android.synthetic.main.fragment_report_product_sale.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class ReportProductSale : Fragment() {
    var calender = Calendar.getInstance()

    @Inject
    lateinit var reportVM: ReportVM
    lateinit var adapterReportProductDay: AdapterReportProductDay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_product_sale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        tanggal_prod.text = currentDate

        list_prod.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        reportVM.data_report_product?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapterReportProductDay = AdapterReportProductDay(it,requireActivity())
            list_prod.adapter = adapterReportProductDay
        })
        reportVM.get_sale_day(requireContext(),currentDate)

        val dateProd = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calender.set(Calendar.YEAR,year)
                calender.set(Calendar.MONTH,month)
                calender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                updateTgl()
            }
        }
        tanggal_prod.setOnClickListener(object :View.OnClickListener{
            override fun onClick(view: View?) {
                DatePickerDialog(context!!,
                    dateProd,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    private fun updateTgl() {
        val formatTgl = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(formatTgl,Locale.US)
        tanggal_prod.text = sdf.format(calender.time)
        list_prod.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        reportVM.data_report_product?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapterReportProductDay = AdapterReportProductDay(it,requireActivity())
            list_prod.adapter = adapterReportProductDay
        })
        reportVM.get_sale_day(requireContext(),sdf.format(calender.time))
    }


}