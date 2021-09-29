package com.projectassyifa.syifamilk.data.payment.vm

import androidx.lifecycle.MutableLiveData
import com.projectassyifa.syifamilk.data.payment.model.PaymentMethodModel
import com.projectassyifa.syifamilk.data.payment.repo.PaymentRepo
import javax.inject.Inject

class PaymentVM @Inject constructor(val paymentRepo: PaymentRepo){
    val p_method : MutableLiveData<List<PaymentMethodModel>>? = paymentRepo.p_method
}