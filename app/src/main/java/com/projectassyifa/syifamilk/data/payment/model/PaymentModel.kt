package com.projectassyifa.syifamilk.data.payment.model

class PaymentModel {
    var id_transaction : String =""
    var payment_method : String = ""
    var customer_id : String =""
    var customer_name : String =""
    var total_payment : Int = 0
    var remaining : Int = 0
    var created_by : String =""

}


class PaymentMethodModel {
    var id_payment_method : String =""
    var name_payment_method : String =""
    var logo : String =""
}

class PaymentReportModel {
    var id_payment : String = ""
    var id_transactions : String =""
    var name_payment_method : String =""
    var logo : String =""
    var customer_id : String =""
    var customer_name : String =""
    var total_payment : String =""
    var remaining : String =""
    var status : String =""
    var tanggal : String =""
}
