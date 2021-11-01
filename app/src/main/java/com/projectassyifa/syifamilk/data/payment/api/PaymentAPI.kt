package com.projectassyifa.syifamilk.data.payment.api

import com.projectassyifa.syifamilk.data.payment.model.PaymentModel
import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentAPI {

    //payment proses
    @POST("payment")
    fun post_payment(@Body paymentModel: PaymentModel):Call<ResponseAPI>

    //get method payment
    @GET("payment/payment_method")
    fun get_payment_method(): Call<ResponseAPI>

    // get report
    @GET("payment/tgl/{tanggal}")
    fun get_payment(@Path("tanggal")tanggal : String):Call<ResponseAPI>
}