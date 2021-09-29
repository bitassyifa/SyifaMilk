package com.projectassyifa.syifamilk.data.user.api

import com.projectassyifa.syifamilk.data.user.model.UserModel
import com.projectassyifa.syifamilk.utils.ResponseAPI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {
    //endpoint get user
    @GET("user")
    fun user():Call<ResponseAPI>

    //endpoint add user
    @Multipart
    @POST("user")
    fun add_user(@Part("username")username : RequestBody,
                 @Part("fullname")fullname : RequestBody,
                 @Part("email")email : RequestBody,
                 @Part("no_tlp")no_tlp : RequestBody,
                 @Part("password")password : RequestBody,
                 @Part photo_user : MultipartBody.Part? = null
    ): Call<ResponseAPI>

    //endpoint delete user
    @DELETE("user/{user_id}")
    fun delete_user(@Path("user_id")user_id : String): Call<ResponseAPI>

    //endppont update user
    @Multipart
    @POST("update_user/{user_id}")
    fun update_user(@Path("user_id")user_id: String,
                    @Part("username")username : RequestBody,
                    @Part("fullname")fullname : RequestBody,
                    @Part("email")email : RequestBody,
                    @Part("no_tlp")no_tlp : RequestBody,
                    @Part("role")role : RequestBody,
                    @Part photo_user : MultipartBody.Part? = null
    ): Call<ResponseAPI>

}