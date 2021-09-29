package com.projectassyifa.syifamilk.data.login.api

import com.projectassyifa.syifamilk.data.login.model.LoginModel
import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {
    @POST("login")
    fun loginUser(@Body userLoginModel : LoginModel): Call<ResponseAPI>
}