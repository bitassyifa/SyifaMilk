package com.projectassyifa.syifamilk.data.report.repo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.syifamilk.data.report.api.ReportAPI
import com.projectassyifa.syifamilk.data.report.model.ReportBalanceModel
import com.projectassyifa.syifamilk.data.report.model.ReportIncomeModel
import com.projectassyifa.syifamilk.data.report.model.ReportProductModel
import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class ReportRepo @Inject constructor(val reportAPI: ReportAPI) {
    var data_report_product : MutableLiveData<List<ReportProductModel>> = MutableLiveData()
    var data_report_income : MutableLiveData<ReportIncomeModel> = MutableLiveData()
    var data_report_balance : MutableLiveData<ReportBalanceModel> = MutableLiveData()


    fun get_sale_product(context: Context){
        reportAPI.get_sale_product().enqueue(object : Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resData = response.body()?.data

                if (response.code() == 200){
                    val objectData : Type = object : TypeToken<List<ReportProductModel>>() {}.type
                    val resValue : List<ReportProductModel> = Gson().fromJson(Gson().toJson(resData),objectData)
                    data_report_product.value = resValue
                } else {
                    Toast.makeText(
                        context,
                        "error product report : ${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                println(t.localizedMessage)
            }

        })
    }

    fun get_sale_day(context: Context,tanggal:String){
        reportAPI.get_sale_day(tanggal).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resData = response.body()?.data

                if (response.body()?.status == true ){
                    val objectData : Type = object : TypeToken<List<ReportProductModel>>() {}.type
                    val resValue : List<ReportProductModel> = Gson().fromJson(Gson().toJson(resData),objectData)
                    data_report_product.value = resValue
                } else {
                    Toast.makeText(
                        context,
                        " ${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    val objectData : Type = object : TypeToken<List<ReportProductModel>>() {}.type
                    val resValue : List<ReportProductModel> = Gson().fromJson(Gson().toJson(resData),objectData)
                    data_report_product.value = resValue
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                println(t.localizedMessage)
                Toast.makeText(
                    context,
                    "Terjadi kesalahan saat terhubung ke server",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun get_income(context: Context){
        reportAPI.get_income().enqueue(object : Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
               val resData = response.body()?.data
                if (response.code() == 200){
                    val objectData : Type = object : TypeToken<List<ReportIncomeModel>>() {}.type
                    val resValue :List<ReportIncomeModel> = Gson().fromJson(Gson().toJson(resData),objectData)
                    data_report_income.value = resValue[0]
                }else {
                    Toast.makeText(
                        context,
                        "error income report : ${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                println(t.localizedMessage)
            }

        })
    }

    fun get_income_balance(context: Context){
        reportAPI.get_income_balance().enqueue(object:Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resData = response.body()?.data
                if (response.code() == 200){
                    val objectData : Type = object : TypeToken<List<ReportBalanceModel>>() {}.type
                    val resValue :List<ReportBalanceModel> = Gson().fromJson(Gson().toJson(resData),objectData)
                    data_report_balance.value = resValue[0]
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                println(t.localizedMessage)
            }

        })
    }
}