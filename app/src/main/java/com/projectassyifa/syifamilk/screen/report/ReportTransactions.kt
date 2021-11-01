package com.projectassyifa.syifamilk.screen.report

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.transaction.adapter.AdapterReportTrans
import com.projectassyifa.syifamilk.data.transaction.vm.TransactionVM
import kotlinx.android.synthetic.main.fragment_report_transactions.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class ReportTransactions : Fragment() {

    var calender = Calendar.getInstance()

    @Inject
    lateinit var transactionVM: TransactionVM
    lateinit var adapterReportTrans: AdapterReportTrans

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_transactions, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        tanggal_trans.text = currentDate
        list_transactions.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        transactionVM.data_transaksi?.observe(viewLifecycleOwner, Observer {
            adapterReportTrans = AdapterReportTrans(it,requireActivity())
            list_transactions.adapter = adapterReportTrans
        })
        transactionVM.get_trans(requireContext(),currentDate)

        val dateTrans = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calender.set(Calendar.YEAR,year)
                calender.set(Calendar.MONTH,month)
                calender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                updateTgl()
            }
        }

        tanggal_trans.setOnClickListener(object :View.OnClickListener{
            override fun onClick(view: View?) {
                DatePickerDialog(context!!,
                    dateTrans,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

    }

    private fun updateTgl() {
        val formatTgl = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(formatTgl,Locale.US)
        tanggal_trans.text = sdf.format(calender.time)
        list_transactions.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        transactionVM.data_transaksi?.observe(viewLifecycleOwner, Observer {
            adapterReportTrans = AdapterReportTrans(it,requireActivity())
            list_transactions.adapter = adapterReportTrans
        })
        transactionVM.get_trans(requireContext(),sdf.format(calender.time))
    }
}