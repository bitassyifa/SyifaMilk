package com.projectassyifa.syifamilk.data.payment.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.projectassyifa.syifamilk.data.payment.model.PaymentMethodModel
import com.projectassyifa.syifamilk.data.payment.model.PaymentModel
import com.projectassyifa.syifamilk.data.payment.repo.PaymentRepo
import com.projectassyifa.syifamilk.utils.ResponseAPI
import javax.inject.Inject

class PaymentVM @Inject constructor(val paymentRepo: PaymentRepo){
    val p_method : MutableLiveData<List<PaymentMethodModel>>? = paymentRepo.p_method
    var responseAPI = paymentRepo.responseAPI
    fun get_payment_method(context: Context){
        paymentRepo.get_payment_method(context)
    }


    fun post_payment(context: Context,paymentModel: PaymentModel){
        paymentRepo.post_payment(context,paymentModel)
    }
}