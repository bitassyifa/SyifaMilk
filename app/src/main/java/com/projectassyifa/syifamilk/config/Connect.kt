package com.projectassyifa.syifamilk.config

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Connect {
    companion object{


        //production
        val BASE_URL_GLOBAL = "http://202.62.9.138/syifamilk_api/api/"
        val BASE_URL_SYIFAMILK =  "http://202.62.9.138/syifamilk_api/api/syifamilk/"

        //dev
//        val BASE_URL_GLOBAL = "http://202.62.9.138/syifamilk_api_dev/api/"
//        val BASE_URL_SYIFAMILK =  "http://202.62.9.138/syifamilk_api_dev/api/syifamilk/"

        var okHttpClient: OkHttpClient? = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()


        fun urlUser(): Retrofit {
            val connection = Retrofit
                .Builder()
                .baseUrl(BASE_URL_SYIFAMILK)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return  connection
        }
        fun urlGlobal():Retrofit{
            val connection = Retrofit
                .Builder()
                .baseUrl(BASE_URL_GLOBAL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return connection
        }
    }
}