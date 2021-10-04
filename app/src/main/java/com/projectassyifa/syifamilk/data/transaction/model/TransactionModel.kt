package com.projectassyifa.syifamilk.data.transaction.model

import java.lang.reflect.Array

class TransactionModel (

    //transaction all
    var id_transactions : String ="",
    var total_payment : String ="",
    var status :String ="",
    var tanggal : String ="",
    var created_by : String ="",
    var created_date : String=""
)


class TransactionModel_post (

    //post
    var created_by : String ="",
    var data_product : ArrayList<DataProduct>

)
{}

class DataProduct(
    var product_id : String = "",
    var product_qty : Int = 0,
    var item_price : Int = 0,
)
{}

class OrderModel(
    var id_transactions : String ="",
    var product_id : String ="",
    var product_name : String ="",
    var product_qty : String ="",
    var total_price_item : String =""
)
{}
