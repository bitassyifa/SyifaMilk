package com.projectassyifa.syifamilk.data.product.repo

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.syifamilk.data.product.api.ProductAPI
import com.projectassyifa.syifamilk.data.product.model.ProductModel
import com.projectassyifa.syifamilk.screen.alert.*
import com.projectassyifa.syifamilk.screen.product.ManagProductActivity
import com.projectassyifa.syifamilk.utils.ResponseAPI
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductRepo @Inject constructor(var productAPI: ProductAPI) {
    var list_product : MutableLiveData<List<ProductModel>> = MutableLiveData()
    var response_product = MutableLiveData<ResponseAPI>()

    //get product
    fun product(context: Context){
     productAPI.product().enqueue(object : Callback<ResponseAPI>{
         override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
             val resData = response.body()?.data
             if (response.code() == 200){
                val gson = Gson()
                 val dataProduct : Type = object : TypeToken<List<ProductModel>?>() {}.type
                 val dataContent : List<ProductModel> = gson.fromJson(gson.toJson(resData),dataProduct)
                 list_product.value = dataContent

             }
         }

         override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
             t.printStackTrace()
             Toast.makeText(
                 context,
                 "Kesalahan server! atau cek koneksi anda",
                 Toast.LENGTH_SHORT
             ).show()
         }

     })
    }

    // add product
    fun add_product(context: Context,data: ProductModel,file :File){
        val product_name = convert(data.product_name)
        val product_price = convert(data.product_price)
        val product_description = convert(data.product_description)
        val product_stock = convert(data.product_stock)
        val product_category = convert(data.product_category)
        val created_by = convert(data.created_by)
        val photo_product = convertFile(file)

        val aktivitas : ManagProductActivity =  context as  ManagProductActivity

        productAPI.add_product(product_name, product_price, product_description, product_stock, product_category, created_by, photo_product).enqueue(object :Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()

                if (response.code() == 200){
                    val resData = Gson().toJson(res)
                    val resContent = Gson().fromJson<ResponseAPI>(resData,ResponseAPI::class.java)
                    response_product.value = resContent
//                    aktivitas.finishMe()
                    val animate = Successfully(context as Activity)
                    animate.startAnimate()
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            animate.isDismiss()
                        }

                    }, 2000)

//                    SystemClock.sleep(2000)

                    Toast.makeText(context,"${res?.message} ", Toast.LENGTH_SHORT).show()
                } else {
                    if (res != null) {
                        Toast.makeText(context,"Error : ${res.message} ", Toast.LENGTH_SHORT).show()
                    }
                    val animate = Wrong(context as Activity)
                    animate.startAnimate()
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            animate.isDismiss()
                        }

                    }, 3000)
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                val animate = NoConnection(context as Activity)
                animate.startAnimate()
                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        animate.isDismiss()
                    }

                }, 3000)
                Toast.makeText(context,"Error : ${t.printStackTrace()} ", Toast.LENGTH_SHORT).show()
            }

        })
    }



    fun delete_product(product_id : String,context: Context){
        productAPI.delete_product(product_id).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                if (response.code() == 200){
//                    val animate = DeleteSucces(context as Activity)
//                    animate.startAnimate()
//                    val handler = Handler()
//                    handler.postDelayed(object : Runnable {
//                        override fun run() {
//                            animate.isDismiss()
//                        }
//
//                    }, 3000)

                    val resData = Gson().toJson(res)
                    val resContent = Gson().fromJson<ResponseAPI>(resData,ResponseAPI::class.java)
                    response_product.value = resContent

                    Toast.makeText(
                        context,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
//                    val animate = DeleteFailed(context as Activity)
//                    animate.startAnimate()
//                    val handler = Handler()
//                    handler.postDelayed(object : Runnable {
//                        override fun run() {
//                            animate.isDismiss()
//                        }
//
//                    }, 3000)
                    Toast.makeText(
                        context,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                Toast.makeText(
                    context,
                    "error connection",
                    Toast.LENGTH_SHORT
                ).show()
                val animate = NoConnection(context as Activity)
                animate.startAnimate()
                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        animate.isDismiss()
                    }

                }, 3000)
            }

        })
    }

    //update product
    fun update_product(context: Context,product_id: String,data : ProductModel,file: File){

        val product_name = convert(data.product_name)
        val product_price = convert(data.product_price)
        val product_description = convert(data.product_description)
        val product_stock = convert(data.product_stock)
        val product_category = convert(data.product_category)
        val updated_by = convert(data.updated_by)
        val photo_product = convertFile(file)

        println("DATA  REPO product_ID $product_id")
        println("DATA REPO product_name ${data.product_name}")
        println("DATA REPO product_price ${data.product_price}")
        println("DATA REPO product_stock ${data.product_stock}")
        println("DATA REPO product_description ${data.product_description}")
        println("DATA REPO product_category ${data.product_category}")
        println("DATA REPO update by ${data.updated_by}")

        productAPI.update_product(product_id, product_name, product_price, product_description, product_stock, product_category, updated_by,photo_product).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()

                if (response.code() == 200){
                    val resData = Gson().toJson(res)
                    val resContent = Gson().fromJson<ResponseAPI>(resData,ResponseAPI::class.java)
                    response_product.value = resContent
//                    aktivitas.finishMe()
                    val animate = Successfully(context as Activity)
                    animate.startAnimate()
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            animate.isDismiss()
                        }

                    }, 2000)

//                    SystemClock.sleep(2000)

                    Toast.makeText(context,"${res?.message} ", Toast.LENGTH_SHORT).show()
                } else {

                        Toast.makeText(context,"Error : ${res?.message} ", Toast.LENGTH_SHORT).show()

                    val animate = Wrong(context as Activity)
                    animate.startAnimate()
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            animate.isDismiss()
                        }

                    }, 3000)
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                val animate = NoConnection(context as Activity)
                animate.startAnimate()
                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        animate.isDismiss()
                    }

                }, 3000)
                Toast.makeText(context,"Error : ${t.printStackTrace()} ", Toast.LENGTH_SHORT).show()
            }

        })

    }

    //update no photo
    fun update_product1(context: Context,product_id: String,data : ProductModel){

        val product_name = convert(data.product_name)
        val product_price = convert(data.product_price)
        val product_description = convert(data.product_description)
        val product_stock = convert(data.product_stock)
        val product_category = convert(data.product_category)
        val updated_by = convert(data.updated_by)

        println("DATA  REPO product_ID $product_id")
        println("DATA REPO product_name ${data.product_name}")
        println("DATA REPO product_price ${data.product_price}")
        println("DATA REPO product_stock ${data.product_stock}")
        println("DATA REPO product_description ${data.product_description}")
        println("DATA REPO product_category ${data.product_category}")
        println("DATA REPO update by ${data.updated_by}")

        productAPI.update_product(product_id, product_name, product_price, product_description, product_stock, product_category, updated_by).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()

                if (response.code() == 200){
                    val resData = Gson().toJson(res)
                    val resContent = Gson().fromJson<ResponseAPI>(resData,ResponseAPI::class.java)
                    response_product.value = resContent
//                    aktivitas.finishMe()
                    val animate = Successfully(context as Activity)
                    animate.startAnimate()
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            animate.isDismiss()
                        }

                    }, 2000)

//                    SystemClock.sleep(2000)

                    Toast.makeText(context,"${res?.message} ", Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(context,"Error : ${res?.message} ", Toast.LENGTH_SHORT).show()

                    val animate = Wrong(context as Activity)
                    animate.startAnimate()
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            animate.isDismiss()
                        }

                    }, 3000)
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                val animate = NoConnection(context as Activity)
                animate.startAnimate()
                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        animate.isDismiss()
                    }

                }, 3000)
                Toast.makeText(context,"Error : ${t.printStackTrace()} ", Toast.LENGTH_SHORT).show()
            }

        })
    }

    //search product
    fun search_product(context: Context,keyword : String){
        productAPI.search_product(keyword).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resData = response.body()?.data
                if (response.code() == 200){
                    val gson = Gson()
                    val dataProduct : Type = object : TypeToken<List<ProductModel>?>() {}.type
                    val dataContent : List<ProductModel> = gson.fromJson(gson.toJson(resData),dataProduct)
                    list_product.value = dataContent
                } else {
                    val animate = NotFound(context as Activity)
                    animate.startAnimate()
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            animate.isDismiss()
                        }

                    }, 3000)
//                    Toast.makeText(
//                        context,
//                        "Product yang di cari tidak ada",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    "Kesalahan server! atau cek koneksi anda",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    //by category
    fun by_category(context: Context,category_id:Int){
        productAPI.by_category(category_id).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val resData = response.body()?.data
                if (response.code() == 200){
                    val gson = Gson()
                    val dataProduct : Type = object : TypeToken<List<ProductModel>?>() {}.type
                    val dataContent : List<ProductModel> = gson.fromJson(gson.toJson(resData),dataProduct)
                    list_product.value = dataContent

                    println("DATA BY CATEGORY ${list_product.value}")
                } else {
                    val animate = NotFound(context as Activity)
                    animate.startAnimate()
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            animate.isDismiss()
                        }

                    }, 3000)
//                    Toast.makeText(
//                        context,
//                        "Product yang di cari tidak ada",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    "Kesalahan server! atau cek koneksi anda",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun convert (string:String) : RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(),string)
    }

    private fun convertFile(file:File): MultipartBody.Part{
        val reqFile : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(),file)
        return MultipartBody.Part.createFormData("photo_product",file.name,reqFile)
    }
}

