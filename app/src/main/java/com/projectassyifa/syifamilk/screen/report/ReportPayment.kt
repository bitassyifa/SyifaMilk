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
import com.projectassyifa.syifamilk.data.payment.adapter.AdapterReportPayment
import com.projectassyifa.syifamilk.data.payment.vm.PaymentVM
import kotlinx.android.synthetic.main.fragment_report_payment.*
import javax.inject.Inject


class ReportPayment : Fragment() {


    @Inject
    lateinit var paymentVM: PaymentVM
    lateinit var adapterReportPayment: AdapterReportPayment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_payment.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        paymentVM.report_pay?.observe(viewLifecycleOwner, Observer {
            adapterReportPayment = AdapterReportPayment(it,requireActivity())
            list_payment.adapter =adapterReportPayment

        })
        paymentVM.get_payment(requireContext())
    }

}