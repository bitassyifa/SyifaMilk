package com.projectassyifa.syifamilk.screen.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.transaction.adapter.AdapterReportTrans
import com.projectassyifa.syifamilk.data.transaction.vm.TransactionVM
import kotlinx.android.synthetic.main.fragment_report_transactions.*
import javax.inject.Inject


class ReportTransactions : Fragment() {

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

        list_transactions.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        transactionVM.data_transaksi?.observe(viewLifecycleOwner, Observer {
            adapterReportTrans = AdapterReportTrans(it,requireActivity())
            list_transactions.adapter = adapterReportTrans
        })
        transactionVM.get_trans(requireContext())

    }
}