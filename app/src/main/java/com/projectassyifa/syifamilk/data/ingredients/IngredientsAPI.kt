package com.projectassyifa.syifamilk.data.ingredients

import com.projectassyifa.syifamilk.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.*

interface IngredientsAPI {

    @POST("ingredients")
    fun postIngredients(@Body ingredientsModel: IngredientsModel) :Call<ResponseAPI>

    @GET("ingredients/byDate/{tanggal}")
    fun getIngredients(@Path("tanggal")tanggal : String): Call<ResponseAPI>

    @DELETE("ingredients/{id_ingredients}")
    fun deleteIngredients(@Path("id_ingredients")id_ingredients:String):Call<ResponseAPI>

    @POST("ingredients/update/{id_ingredients}")
    fun updateIngredients(@Path("id_ingredients")id_ingredients: String,
                          @Body ingredientsModel: IngredientsModel):Call<ResponseAPI>

    @GET("ingredients/totalPerDay/{tanggal}")
    fun getTotalIngredients(@Path("tanggal")tanggal: String):Call<ResponseAPI>
}