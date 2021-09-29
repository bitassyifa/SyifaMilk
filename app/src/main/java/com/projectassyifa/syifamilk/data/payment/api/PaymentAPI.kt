package com.projectassyifa.syifamilk.data.payment.api

import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.GET

interface PaymentAPI {

    //get method payment
    @GET("payment/payment_method")
    fun get_payment_method(): Call<ResponseAPI>
}