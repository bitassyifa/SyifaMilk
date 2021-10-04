package com.projectassyifa.syifamilk.screen.oder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.transaction.adapter.AdapterOrder
import com.projectassyifa.syifamilk.data.transaction.vm.TransactionVM
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.fragment_order_detail.*
import javax.inject.Inject


class OrderDetail : DialogFragment()  {

    @Inject
    lateinit var transactionVM: TransactionVM
    lateinit var adapterOrder : AdapterOrder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        val id_trans = bundle!!.getString("id_trans")
        id_trans1.text = id_trans
        order_list1.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        transactionVM.data_order?.observe(viewLifecycleOwner, Observer {
            adapterOrder = AdapterOrder(it,this.requireActivity())
            order_list1.adapter = adapterOrder
            println("LIST ADAPTER ${adapterOrder.list_order[0].product_name}")
        })
        transactionVM.order_id(this.requireContext(),id_trans!!)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.60).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
    }

}