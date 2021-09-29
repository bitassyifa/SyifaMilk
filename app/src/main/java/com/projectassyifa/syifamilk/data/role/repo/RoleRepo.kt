package com.projectassyifa.syifamilk.data.role.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.syifamilk.data.role.api.RoleAPI
import com.projectassyifa.syifamilk.data.role.model.RoleModel
import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class RoleRepo @Inject constructor(var roleAPI: RoleAPI) {
    var data_role : MutableLiveData<List<RoleModel>> = MutableLiveData()

    fun role(){
        roleAPI.role().enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resBody = response.body()
                val data = resBody?.data

                if (data == null){
                    println("null")
                } else {
                    val gson = Gson()
                    val listData : Type = object : TypeToken<List<RoleModel>?>() {}.type
                    val resData : List<RoleModel> = gson.fromJson(gson.toJson(data),listData)
                    data_role.value = resData
                    println("ROLE ${data_role.value}")
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
              t.printStackTrace()
            }

        })
    }
}