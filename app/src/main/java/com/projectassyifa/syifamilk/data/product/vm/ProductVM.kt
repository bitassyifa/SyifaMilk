package com.projectassyifa.syifamilk.data.product.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.projectassyifa.syifamilk.data.product.model.ProductModel
import com.projectassyifa.syifamilk.data.product.repo.ProductRepo
import com.projectassyifa.syifamilk.utils.ResponseAPI
import java.io.File
import javax.inject.Inject

class ProductVM @Inject constructor(val productRepo: ProductRepo) {
    val list_product : MutableLiveData<List<ProductModel>>? = productRepo.list_product
    val response_product : MutableLiveData<ResponseAPI> = productRepo.response_product

    fun product(context: Context){
        productRepo.product(context)
    }
    fun add_product(context: Context,productModel: ProductModel,file: File){
        productRepo.add_product(context,productModel,file)
    }
    fun delete_product(product_id : String , context: Context){
        productRepo.delete_product(product_id,context)
    }
    fun update_product(context: Context,product_id: String,data:ProductModel,file: File){
        productRepo.update_product(context,product_id, data, file)
    }
    fun update_product1(context: Context,product_id: String,data:ProductModel){
        productRepo.update_product1(context,product_id, data)
    }
    fun search_product(context: Context,keyword:String){
        productRepo.search_product(context,keyword)
    }
    fun by_category(context: Context,category_id : Int){
        productRepo.by_category(context, category_id)
    }
}