package com.projectassyifa.syifamilk.data.category.repo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.syifamilk.data.category.api.CategoryAPI
import com.projectassyifa.syifamilk.data.category.model.CategoryModel
import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class CategoryRepo @Inject constructor(var categoryAPI: CategoryAPI) {
    var data_category : MutableLiveData<List<CategoryModel>> = MutableLiveData()

    fun category(context:Context){
        categoryAPI.category().enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val data = response.body()?.data
                val gson = Gson()
                val listData : Type = object : TypeToken<List<CategoryModel>?>() {}.type
                val resData : List<CategoryModel> = gson.fromJson(gson.toJson(data),listData)
                data_category.value = resData
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Error : ${t.printStackTrace()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}