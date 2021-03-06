package com.projectassyifa.syifamilk.data.payment.repo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.syifamilk.data.payment.api.PaymentAPI
import com.projectassyifa.syifamilk.data.payment.model.PaymentMethodModel
import com.projectassyifa.syifamilk.data.payment.model.PaymentModel
import com.projectassyifa.syifamilk.data.payment.model.PaymentReportModel
import com.projectassyifa.syifamilk.data.product.model.ProductModel
import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class PaymentRepo @Inject constructor(var paymentAPI: PaymentAPI) {
    var p_method : MutableLiveData<List<PaymentMethodModel>> = MutableLiveData()
    var report_pay : MutableLiveData<List<PaymentReportModel>> = MutableLiveData()
    var responseAPI = MutableLiveData<ResponseAPI>()


    //get report
    fun get_payment(context: Context,tanggal:String){
        paymentAPI.get_payment(tanggal).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resData = response.body()?.data
                println(" DATAPAY $resData")
                println(" MESSAGE ${response.body()?.message}")
                if ( response.body()?.status == true ){
                    val gson = Gson()
                    val dataReport : Type = object : TypeToken<List<PaymentReportModel>?>() {}.type
                    val dataContent : List<PaymentReportModel> = gson.fromJson(gson.toJson(resData),dataReport)
                    report_pay.value = dataContent
                } else {
                    Toast.makeText(
                        context,
                        "Data Tidak Ditemukan",
                        Toast.LENGTH_SHORT
                    ).show()
                    val gson = Gson()
                    val dataReport : Type = object : TypeToken<List<PaymentReportModel>?>() {}.type
                    val dataContent : List<PaymentReportModel> = gson.fromJson(gson.toJson(resData),dataReport)
                    report_pay.value = dataContent
                }
            }
            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    "Kesalahan server! Saat membuka method payment",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }


    //get payment method
    fun get_payment_method(context: Context){
        paymentAPI.get_payment_method().enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resData = response.body()?.data
                if (response.code() == 200) {
                    val gson = Gson()
                    val dataProduct: Type = object : TypeToken<List<PaymentMethodModel>?>() {}.type
                    val dataContent: List<PaymentMethodModel> =
                        gson.fromJson(gson.toJson(resData), dataProduct)
                    p_method.value = dataContent
                }
            }
            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    "Kesalahan server! Saat membuka method payment",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun post_payment(context: Context,paymentModel: PaymentModel){
        paymentAPI.post_payment(paymentModel).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resData = response.body()
                if (response.code() == 200){
                    Toast.makeText(
                        context,
                        "${resData?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    responseAPI.value = resData
                } else {
                    Toast.makeText(
                        context,
                        "${resData?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    "Kesalahan server! Saat membuka method payment",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

}