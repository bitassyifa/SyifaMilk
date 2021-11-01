package com.projectassyifa.syifamilk.data.ingredients

import android.content.Context
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class IngredientsVM @Inject constructor(val ingredientsRepo: IngredientsRepo){
    var dataIngredients : MutableLiveData<List<IngredientsModel>>? = ingredientsRepo.dataIngredients
    var responseAPI = ingredientsRepo.responseAPI
    var total = ingredientsRepo.totalIngre

    fun getIngredients(context: Context,tanggal:String){
        ingredientsRepo.getIngredients(context, tanggal)
    }
    fun postIngredients(context: Context,ingredientsModel: IngredientsModel){
        ingredientsRepo.postIngredients(context,ingredientsModel)
    }
    fun deleteIngredients(context: Context,id_ingredients:String){
        ingredientsRepo.deleteIngredients(context,id_ingredients)
    }

    fun updateIngredients(context: Context,id_ingredients: String,ingredientsModel: IngredientsModel){
        ingredientsRepo.updateIngredients(context, id_ingredients, ingredientsModel)
    }

    fun getTotalIngredients(context: Context,tanggal: String){
        ingredientsRepo.getTotalIngredients(context, tanggal)
    }


}