package com.projectassyifa.syifamilk.container

import com.projectassyifa.syifamilk.data.cart.adapter.AdapterCart
import com.projectassyifa.syifamilk.data.ingredients.AdapterIngredients
import com.projectassyifa.syifamilk.data.payment.adapter.AdapterReportPayment
import com.projectassyifa.syifamilk.data.product.adapter.AdapterManagProduct
import com.projectassyifa.syifamilk.data.product.adapter.AdapterProduct
import com.projectassyifa.syifamilk.data.report.adapter.AdapterReportProductDay
import com.projectassyifa.syifamilk.data.transaction.adapter.AdapterOrder
import com.projectassyifa.syifamilk.data.transaction.adapter.AdapterReportTrans
import com.projectassyifa.syifamilk.data.user.adapter.AdapterUser
import com.projectassyifa.syifamilk.screen.dashboard.DashboardActivity
import com.projectassyifa.syifamilk.screen.ingredients.AddIngredients
import com.projectassyifa.syifamilk.screen.ingredients.IngredientsActivity
import com.projectassyifa.syifamilk.screen.ingredients.UpdateIngredients
import com.projectassyifa.syifamilk.screen.login.LoginLayout
import com.projectassyifa.syifamilk.screen.oder.OrderActivity
import com.projectassyifa.syifamilk.screen.oder.OrderDetail
import com.projectassyifa.syifamilk.screen.payment.PaymentActivity
import com.projectassyifa.syifamilk.screen.product.AddProductLayout
import com.projectassyifa.syifamilk.screen.product.ManagProductActivity
import com.projectassyifa.syifamilk.screen.product.UpdateProductLayout
import com.projectassyifa.syifamilk.screen.report.ReportPayment
import com.projectassyifa.syifamilk.screen.report.ReportProductSale
import com.projectassyifa.syifamilk.screen.report.ReportTransactions
import com.projectassyifa.syifamilk.screen.user.AdduserLayout
import com.projectassyifa.syifamilk.screen.user.ManagementUser
import com.projectassyifa.syifamilk.screen.user.UpdateUserLayout
import dagger.Component

@Component(modules = [NetworkModul::class])
interface ApplicationComponent {
    fun inject(managementUser: ManagementUser)
    fun inject(orderActivity: OrderActivity)
    fun inject(adapterProduct: AdapterProduct)
    fun inject(loginLayout: LoginLayout)
    fun inject(adduserLayout: AdduserLayout)
    fun inject(adapterUser: AdapterUser)
    fun inject(updateUserLayout: UpdateUserLayout)
    fun inject(adapterManagProduct: AdapterManagProduct)
    fun inject(managProductActivity: ManagProductActivity)
    fun inject(addProductLayout: AddProductLayout)
    fun inject(updateProductLayout: UpdateProductLayout)
    fun inject(adapterCart: AdapterCart)
    fun inject(paymentActivity: PaymentActivity)
    fun inject(adapterOrder: AdapterOrder)
    fun inject(adapterReportTrans: AdapterReportTrans)
    fun inject(reportTransactions: ReportTransactions)
    fun inject(orderDetail: OrderDetail)
    fun inject(adapterReportPayment: AdapterReportPayment)
    fun inject(reportPayment: ReportPayment)
    fun inject(dashboardActivity: DashboardActivity)
    fun inject(adapterReportProductDay: AdapterReportProductDay)
    fun inject(reportProductSale: ReportProductSale)
    fun inject(ingredientsActivity: IngredientsActivity)
    fun inject(addIngredients: AddIngredients)
    fun inject(adapterIngredients: AdapterIngredients)
    fun inject(updateIngredients: UpdateIngredients)
}