package com.projectassyifa.syifamilk.data.user.repo

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.projectassyifa.syifamilk.data.user.api.UserAPI
import com.projectassyifa.syifamilk.data.user.model.UserModel
import com.projectassyifa.syifamilk.screen.alert.NoConnection
import com.projectassyifa.syifamilk.screen.alert.Successfully
import com.projectassyifa.syifamilk.screen.alert.Wrong
import com.projectassyifa.syifamilk.utils.ResponseAPI
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class AddUserRepo @Inject constructor(val userAPI: UserAPI) {
    var response_user = MutableLiveData<ResponseAPI>()

    fun add_user(context:Context,data: UserModel,file:File){

        val username = convert(data.username)
        val fullname = convert(data.fullname)
        val email = convert(data.email)
        val no_tlp = convert(data.no_tlp)
        val password = convert(data.password)
        val photo_user = convertFile(file)

        userAPI.add_user(username,fullname, email, no_tlp, password, photo_user).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                println("Message  ${res?.message}")
                if (response.code() == 200) {
                    val resData = Gson().toJson(res)
                    val resContent = Gson().fromJson<ResponseAPI>(resData, ResponseAPI::class.java)
                    response_user.value = resContent
                    val animate = Successfully(context as Activity)
                    animate.startAnimate()
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            animate.isDismiss()
                        }

                    }, 3000)

                } else {
                    val animate = Wrong(context as Activity)
                    animate.startAnimate()
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            animate.isDismiss()
                        }

                    }, 3000)
                    Toast.makeText(
                        context,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                val animate = NoConnection(context as Activity)
                animate.startAnimate()
                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        animate.isDismiss()
                    }

                }, 3000)
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