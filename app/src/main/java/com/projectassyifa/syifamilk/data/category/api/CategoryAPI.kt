package com.projectassyifa.syifamilk.data.category.api

import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.GET

interface CategoryAPI {
    @GET("product/category")
    fun category(): Call<ResponseAPI>
}