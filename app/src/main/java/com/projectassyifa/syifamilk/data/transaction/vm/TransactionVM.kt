package com.projectassyifa.syifamilk.data.transaction.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.projectassyifa.syifamilk.data.transaction.model.DataProduct
import com.projectassyifa.syifamilk.data.transaction.model.OrderModel
import com.projectassyifa.syifamilk.data.transaction.model.TransactionModel
import com.projectassyifa.syifamilk.data.transaction.model.TransactionModel_post
import com.projectassyifa.syifamilk.data.transaction.repo.TransactionRepo
import com.projectassyifa.syifamilk.utils.ResponseAPI
import javax.inject.Inject

class TransactionVM @Inject constructor(val transactionRepo: TransactionRepo) {
    val responseAPI : MutableLiveData<ResponseAPI> = transactionRepo.responseAPI
    val data_transaksi_id : MutableLiveData<TransactionModel> = transactionRepo.data_transaksi_id
    val data_order : MutableLiveData<List<OrderModel>> = transactionRepo.data_order

    fun add_transaction(context: Context,transactionModel: TransactionModel_post){
        transactionRepo.add_transaction(context,transactionModel)
    }

    fun get_trans_id(context: Context,id_transaction: String){
        transactionRepo.get_trans_id(context, id_transaction)
    }

    fun order_id(context: Context,id_transaction: String){
        transactionRepo.order_id(context, id_transaction)
    }
}