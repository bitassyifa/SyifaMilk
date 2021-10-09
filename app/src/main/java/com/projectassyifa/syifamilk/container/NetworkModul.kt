package com.projectassyifa.syifamilk.container

import com.projectassyifa.syifamilk.config.Connect
import com.projectassyifa.syifamilk.data.category.api.CategoryAPI
import com.projectassyifa.syifamilk.data.login.api.LoginAPI
import com.projectassyifa.syifamilk.data.payment.api.PaymentAPI
import com.projectassyifa.syifamilk.data.product.api.ProductAPI
import com.projectassyifa.syifamilk.data.report.api.ReportAPI
import com.projectassyifa.syifamilk.data.role.api.RoleAPI
import com.projectassyifa.syifamilk.data.transaction.api.TransactionAPI
import com.projectassyifa.syifamilk.data.user.api.UserAPI
import dagger.Module
import dagger.Provides
@Module
class NetworkModul {
    @Provides
    fun provideUserAPI(): UserAPI {
        return Connect.urlUser().create(UserAPI::class.java)
    }
    @Provides
    fun provideProductAPI(): ProductAPI {
        return Connect.urlGlobal().create(ProductAPI::class.java)
    }
    @Provides
    fun provideCategoryAPI(): CategoryAPI {
        return Connect.urlGlobal().create(CategoryAPI::class.java)
    }
    @Provides
    fun provideLoginAPI(): LoginAPI{
        return Connect.urlUser().create(LoginAPI::class.java)
    }
    @Provides
    fun provideRoleAPI(): RoleAPI {
        return Connect.urlUser().create(RoleAPI::class.java)
    }
    @Provides
    fun provideTransactionAPI(): TransactionAPI {
        return Connect.urlGlobal().create(TransactionAPI::class.java)
    }
    @Provides
    fun providePaymentAPI(): PaymentAPI {
        return Connect.urlGlobal().create(PaymentAPI::class.java)
    }
    @Provides
    fun provideReportAPI(): ReportAPI {
        return Connect.urlGlobal().create(ReportAPI::class.java)
    }

}