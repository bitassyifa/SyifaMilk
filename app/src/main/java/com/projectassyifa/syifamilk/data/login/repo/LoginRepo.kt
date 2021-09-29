package com.projectassyifa.syifamilk.data.login.repo

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.syifamilk.data.login.api.LoginAPI
import com.projectassyifa.syifamilk.data.login.model.LoginModel
import com.projectassyifa.syifamilk.data.login.model.LoginResponseModel
import com.projectassyifa.syifamilk.screen.alert.NoConnection
import com.projectassyifa.syifamilk.screen.alert.Wrong
import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class LoginRepo @Inject constructor(val LoginApi : LoginAPI) {
    var userLogin = MutableLiveData<ResponseAPI>()
    var userLoginResponse = MutableLiveData<LoginResponseModel>()



    fun loginUser(userLoginModel: LoginModel, context: Context){
        println("USER LOGIN ${userLoginModel.username},${userLoginModel.password}")
       LoginApi.loginUser(userLoginModel).enqueue(object : Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                if (response.code()==200){
                    val res = response.body()
                    val resData = res?.data
                    val gson = Gson()
                    val listOfMyClassObject: Type = object : TypeToken<List<LoginResponseModel>>() {}.type
                    val outputList: List<LoginResponseModel> = gson.fromJson(gson.toJson(resData), listOfMyClassObject)

                    userLogin.value = res
                    userLoginResponse.value = outputList[0]


                    Toast.makeText(context,"Login Success ", Toast.LENGTH_SHORT).show()
                } else {
                    val animate = Wrong(context as Activity)
                    animate.startAnimate()
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            animate.isDismiss()
                        }

                    }, 3000)
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
                Toast.makeText(context,"${t.printStackTrace()} ", Toast.LENGTH_SHORT).show()
            }

        })
    }
}