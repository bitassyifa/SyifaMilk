package com.projectassyifa.syifamilk.data.cart.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projectassyifa.syifamilk.data.cart.model.CartModel

class CartVM :ViewModel() {
    var cartlist = mutableListOf<CartModel>()
    var cartLiveData : MutableLiveData<MutableList<CartModel>> = MutableLiveData(cartlist)

    fun addCartList(
        productID: String, productName: String, productPrice: Int, productQty: Int,
        productImage: String, unitPrice: String){
        cartlist.add(
            CartModel(
                productID,
                productName,
                productPrice,
                productQty,
                productImage,
                unitPrice
            )
        )

        cartLiveData.value = cartlist
        println("DATA CART VM ${cartLiveData.value}")
    }
}