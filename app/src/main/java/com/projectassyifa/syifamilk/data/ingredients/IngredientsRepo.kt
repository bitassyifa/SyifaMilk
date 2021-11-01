package com.projectassyifa.syifamilk.data.ingredients

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class IngredientsRepo @Inject constructor(var ingredientsApi: IngredientsAPI){
    var responseAPI = MutableLiveData<ResponseAPI>()
    var dataIngredients : MutableLiveData<List<IngredientsModel>> = MutableLiveData()
    var totalIngre = MutableLiveData<TotalModel>()

    fun postIngredients(context: Context,ingredientsModel: IngredientsModel){
        ingredientsApi.postIngredients(ingredientsModel).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
               val resData = response.body()
                Toast.makeText(
                    context,
                    "${resData?.message}",
                    Toast.LENGTH_SHORT
                ).show()
                responseAPI.value = resData
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    "Kesalahan server! Cek koneksi ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun updateIngredients(context: Context,id_ingredients: String,ingredientsModel: IngredientsModel){
        ingredientsApi.updateIngredients(id_ingredients, ingredientsModel).enqueue(object :Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resData = response.body()
                Toast.makeText(
                    context,
                    "${resData?.message}",
                    Toast.LENGTH_SHORT
                ).show()
                responseAPI.value = resData
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    "Kesalahan server! Cek koneksi ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getIngredients(context: Context,tanggal: String){
        ingredientsApi.getIngredients(tanggal).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
              val resBody = response.body()
                val resData = resBody?.data
                if (resBody?.status == true ){
                    val gson = Gson()
                    val listData : Type = object : TypeToken<List<IngredientsModel>?>() {}.type
                    val dataContent : List<IngredientsModel> = gson.fromJson(gson.toJson(resData),listData)
                    dataIngredients.value = dataContent
                } else {
                    Toast.makeText(
                        context,
                        "${resBody?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    val gson = Gson()
                    val listData : Type = object : TypeToken<List<IngredientsModel>?>() {}.type
                    val dataContent : List<IngredientsModel> = gson.fromJson(gson.toJson(resData),listData)
                    dataIngredients.value = dataContent
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    "Kesalahan server! Cek koneksi ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getTotalIngredients(context: Context,tanggal: String){
        ingredientsApi.getTotalIngredients(tanggal).enqueue(object :  Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resBody = response.body()
                val resData = resBody?.data
                val gson = Gson()
                val listData : Type = object : TypeToken<List<TotalModel>?>() {}.type
                val dataContent : List<TotalModel> = gson.fromJson(gson.toJson(resData),listData)
                totalIngre.value = dataContent[0]
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    "Kesalahan server! Cek koneksi ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun deleteIngredients(context: Context,id_ingredients:String){
        ingredientsApi.deleteIngredients(id_ingredients).enqueue(object :Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                if (response.code() == 200){
                    Toast.makeText(
                        context,
                        "Delete Success",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Delete Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                Toast.makeText(
                    context,
                    "error connection",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}