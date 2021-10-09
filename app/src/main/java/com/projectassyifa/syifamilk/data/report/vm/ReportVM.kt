package com.projectassyifa.syifamilk.data.report.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.projectassyifa.syifamilk.data.report.model.ReportBalanceModel
import com.projectassyifa.syifamilk.data.report.model.ReportIncomeModel
import com.projectassyifa.syifamilk.data.report.model.ReportProductModel
import com.projectassyifa.syifamilk.data.report.repo.ReportRepo
import javax.inject.Inject

class ReportVM @Inject constructor(val reportRepo: ReportRepo){
    val data_report_product : MutableLiveData<List<ReportProductModel>> = reportRepo.data_report_product
    val data_report_income : MutableLiveData<ReportIncomeModel> = reportRepo.data_report_income
    val data_report_balance : MutableLiveData<ReportBalanceModel> = reportRepo.data_report_balance
    fun get_sale_product(context: Context){
        reportRepo.get_sale_product(context)
    }

    fun get_income(context: Context){
        reportRepo.get_income(context)
    }

    fun get_income_balance(context: Context){
        reportRepo.get_income_balance(context)
    }
}

