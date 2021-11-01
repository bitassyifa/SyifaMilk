package com.projectassyifa.syifamilk.data.product.api

import com.projectassyifa.syifamilk.data.product.model.ProductModel
import com.projectassyifa.syifamilk.utils.ResponseAPI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProductAPI {
    //endpoint get product
    @GET("product")
    fun product():Call<ResponseAPI>

    //endpoint add product
    @Multipart
    @POST("product")
    fun add_product(@Part("product_name")product_name : RequestBody,
                    @Part("starting_price")starting_price : RequestBody,
                    @Part("product_price")product_price : RequestBody,
                    @Part("product_description")product_description : RequestBody,
                    @Part("product_stock")product_stock : RequestBody,
                    @Part("product_category")product_category : RequestBody,
                    @Part("created_by")created_by : RequestBody,
                    @Part photo_product : MultipartBody.Part? = null
    ) :Call<ResponseAPI>

    //endpoint delete product
    @DELETE("product/{product_id}")
    fun delete_product(@Path("product_id")product_id : String) : Call<ResponseAPI>

    //endpoint update product
    @Multipart
    @POST("product/update_product/{product_id}")
    fun update_product(@Path("product_id")product_id: String,
                       @Part("product_name")product_name : RequestBody,
                       @Part("product_price")product_price : RequestBody,
                       @Part("starting_price")starting_price : RequestBody,
                       @Part("product_description")product_description : RequestBody,
                       @Part("product_stock")product_stock : RequestBody,
                       @Part("product_category")product_category : RequestBody,
                       @Part("updated_by")updated_by : RequestBody,
                       @Part photo_product : MultipartBody.Part? = null
                       ) :Call<ResponseAPI>
    // search product
    @GET("product/search_product/{keyword}")
    fun search_product(@Path("keyword")keyword : String ) : Call<ResponseAPI>

    // by category
    @GET("product/bycategory/{category_id}")
    fun by_category(@Path("category_id")category_id : Int) : Call<ResponseAPI>
}