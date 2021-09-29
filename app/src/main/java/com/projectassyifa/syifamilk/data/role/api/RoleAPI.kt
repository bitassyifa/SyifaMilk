package com.projectassyifa.syifamilk.data.role.api

import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.GET

interface RoleAPI {
    @GET("role")
    fun role():Call<ResponseAPI>
}