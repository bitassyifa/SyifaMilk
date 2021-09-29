package com.projectassyifa.syifamilk.data.category.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.projectassyifa.syifamilk.data.category.model.CategoryModel
import com.projectassyifa.syifamilk.data.category.repo.CategoryRepo
import javax.inject.Inject

class CategoryVM @Inject constructor(val categoryRepo: CategoryRepo){
    val data_category : MutableLiveData<List<CategoryModel>>? = categoryRepo.data_category
    fun category(context: Context){
        categoryRepo.category(context)
    }
}