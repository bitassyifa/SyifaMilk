package com.projectassyifa.syifamilk.data.report.api

import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface ReportAPI {

    //report product yang dibeli
    @GET("report")
    fun get_sale_product():Call<ResponseAPI>

    //report income
    @GET("report/income")
    fun get_income():Call<ResponseAPI>

    //report income balance
    @GET("report/income_balance")
    fun get_income_balance():Call<ResponseAPI>

    //productsale per day
    @GET("report/productSale/{tanggal}")
    fun get_sale_day(@Path("tanggal")tanggal : String):Call<ResponseAPI>
}
