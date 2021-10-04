package com.projectassyifa.syifamilk.data.transaction.repo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.syifamilk.data.product.model.ProductModel
import com.projectassyifa.syifamilk.data.transaction.api.TransactionAPI
import com.projectassyifa.syifamilk.data.transaction.model.DataProduct
import com.projectassyifa.syifamilk.data.transaction.model.OrderModel
import com.projectassyifa.syifamilk.data.transaction.model.TransactionModel
import com.projectassyifa.syifamilk.data.transaction.model.TransactionModel_post
import com.projectassyifa.syifamilk.data.user.model.UserModel
import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class TransactionRepo @Inject constructor(val transactionAPI : TransactionAPI){
    var responseAPI = MutableLiveData<ResponseAPI>()
    var data_transaksi : MutableLiveData<List<TransactionModel>> = MutableLiveData()
    var data_transaksi_id : MutableLiveData<TransactionModel> = MutableLiveData()
    var data_order : MutableLiveData<List<OrderModel>> = MutableLiveData()


    //get report transactions
    fun get_trans(context: Context){
        transactionAPI.get_trans().enqueue(object :  Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resbody = response.body()?.data
                if (response.code() == 200){
                    val gson = Gson()
                    val classObject : Type = object  : TypeToken<List<TransactionModel>>() {}.type
                    val resvalue : List<TransactionModel> = gson.fromJson(gson.toJson(resbody),classObject)
                    data_transaksi.value = resvalue

                } else {
                    Toast.makeText(
                        context,
                        "error : ${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                println(t.localizedMessage)
            }

        })
    }

    //add trans
    fun add_transaction(context: Context,transactionmodelPost: TransactionModel_post){
        transactionAPI.add_transactions(transactionmodelPost).enqueue(object : Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response?.body()
                val ResponseData = Gson().toJson(res)
                val apiResponse = Gson().fromJson<ResponseAPI>(ResponseData,ResponseAPI::class.java)
               responseAPI.value = apiResponse
                Toast.makeText(
                    context,
                    "${res?.message} , ${response.code()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                println(t.localizedMessage)
                println("transaction failed")
            }

        })
    }

    //get trans by id
    fun get_trans_id(context: Context,id_transaction:String){
        transactionAPI.get_trans_id(id_transaction).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resbody = response.body()?.data
                if (response.code() == 200){
                    val gson = Gson()
                    val classObject : Type = object  : TypeToken<List<TransactionModel>>() {}.type
                    val resvalue : List<TransactionModel> = gson.fromJson(gson.toJson(resbody),classObject)
                    data_transaksi_id.value = resvalue[0]
                    println("DATA REPO TRANS ${data_transaksi_id.value}")
                } else {
                    Toast.makeText(
                        context,
                        "error : ${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                println(t.localizedMessage)
            }

        })
    }

    //get data order
    fun order_id(context: Context,id_transaction: String){
        transactionAPI.order_id(id_transaction).enqueue(object  : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resData = response.body()?.data
                if (response.code() == 200){
                    val gson = Gson()
                    val dataProduct : Type = object : TypeToken<List<OrderModel>?>() {}.type
                    val dataContent : List<OrderModel> = gson.fromJson(gson.toJson(resData),dataProduct)
                    data_order.value = dataContent

                } else {
                    Toast.makeText(
                        context,
                        "Kesalahan saat menampilkan data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    "Kesalahan server! atau cek koneksi anda",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}