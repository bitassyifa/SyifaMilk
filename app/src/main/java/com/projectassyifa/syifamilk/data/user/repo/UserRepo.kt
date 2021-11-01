package com.projectassyifa.syifamilk.data.user.repo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.syifamilk.data.user.api.UserAPI
import com.projectassyifa.syifamilk.data.user.model.UserModel
import com.projectassyifa.syifamilk.utils.ResponseAPI
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.reflect.Type
import javax.inject.Inject

class UserRepo @Inject constructor(var userAPI: UserAPI) {
    var list_user : MutableLiveData<List<UserModel>> = MutableLiveData()
    var response_user = MutableLiveData<ResponseAPI>()

    //get user
    fun user(context: Context){
        userAPI.user().enqueue(object : Callback<ResponseAPI> {
            override fun onResponse(
                call: Call<ResponseAPI>,
                response: Response<ResponseAPI>
            ) {
                println("data $response")
                println("data body ${response.body()}")
                val res = response.body()
                val resData = res?.data
                if (response.code() == 200){
                    val gson = Gson()
                    val data_user:Type = object : TypeToken<List<UserModel>?>() {}.type
                    val output_user : List<UserModel> = gson.fromJson(gson.toJson(resData),data_user)
                    list_user.value = output_user
                    println("VALUE DATA ${list_user.value}")
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

    //delete user
    fun delete_user(user_id : String,context: Context){
        userAPI.delete_user(user_id).enqueue(object : Callback<ResponseAPI>{
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
                println(t.localizedMessage)
                println("gagal delete")
                Toast.makeText(
                    context,
                    "error connection",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    //update user
    fun update_user(user_id : String,data : UserModel,file : File){
        println("USER ID $user_id")

        val username = convert(data.username)
        val fullname = convert(data.fullname)
        val email = convert(data.email)
        val no_tlp = convert(data.no_tlp)
//        val password = convert(data.password)
        val photo_user = convertFile(file)
        val role = convert(data.role_id)

        userAPI.update_user(user_id, username, fullname, email, no_tlp, role, photo_user).enqueue(
            object :  Callback<ResponseAPI> {
                override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                    val res = response.body()
                    if (response.code() == 200){
                        val resData = Gson().toJson(res)
                        val resContent = Gson().fromJson<ResponseAPI>(resData,ResponseAPI::class.java)
                        response_user.value = resContent
                    } else {
                        println("FAILED UPDATE ")
                    }
                }

                override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                    t.printStackTrace()
                    println("FAILED UPDATE USER")
                }

            })
    }

    //update user no photo
    fun update_user1(user_id : String,data : UserModel){
        println("USER ID $user_id")

        val username = convert(data.username)
        val fullname = convert(data.fullname)
        val email = convert(data.email)
        val role = convert(data.role_id)
        val no_tlp = convert(data.no_tlp)
//        val password = convert(data.password)


        userAPI.update_user(user_id, username, fullname, email, no_tlp,role).enqueue(
            object :  Callback<ResponseAPI> {
                override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                    val res = response.body()
                    if (response.code() == 200){
                        val resData = Gson().toJson(res)
                        val resContent = Gson().fromJson<ResponseAPI>(resData,ResponseAPI::class.java)
                        response_user.value = resContent
                    } else {
                        println("FAILED UPDATE ")
                    }
                }

                override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                    t.printStackTrace()
                    println("FAILED UPDATE USER")
                }

            })
    }

    private fun convert (string :String ) : RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(),string)
    }

    private fun convertFile(file:File): MultipartBody.Part{
        val reqFile : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file)
        return MultipartBody.Part.createFormData("photo_user",file.name,reqFile)
    }



}