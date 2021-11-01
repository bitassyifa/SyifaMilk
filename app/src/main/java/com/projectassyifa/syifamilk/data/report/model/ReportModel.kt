package com.projectassyifa.syifamilk.data.report.model

class ReportProductModel {
    var product_name : String =""
    var product_id : String = ""
    var jumlah: Int =0
}

class ReportIncomeModel{
    var perhari : Int = 0
    var perminggu : Int = 0
    var perbulan : Int =0
    var pertahun : Int =0
}

class ReportBalanceModel{
    var income_transaksi : Int = 0
    var income_payment : Int = 0
}
