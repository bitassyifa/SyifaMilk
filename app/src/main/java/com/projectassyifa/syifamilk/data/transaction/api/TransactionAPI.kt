package com.projectassyifa.syifamilk.data.transaction.api

import com.projectassyifa.syifamilk.data.transaction.model.DataProduct
import com.projectassyifa.syifamilk.data.transaction.model.TransactionModel
import com.projectassyifa.syifamilk.data.transaction.model.TransactionModel_post
import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionAPI {

    //add transaction
    @POST("transactions")
    fun add_transactions(@Body transactionsModel : TransactionModel_post): Call<ResponseAPI>
//        fun add_transactions(@Body data : List<DataProduct>): Call<ResponseAPI>

    //get transaksi by id
    @GET("transactions/{id_transactions}")
    fun get_trans_id(@Path("id_transactions")id_transactions : String) : Call<ResponseAPI>

    //get order transaksi
    @GET("transactions/order/{id_transactions}")
    fun order_id(@Path("id_transactions")id_transactions: String):Call<ResponseAPI>
}