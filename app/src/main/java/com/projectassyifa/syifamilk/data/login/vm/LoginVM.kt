package com.projectassyifa.syifamilk.data.login.vm

import android.content.Context
import com.projectassyifa.syifamilk.data.login.model.LoginModel
import com.projectassyifa.syifamilk.data.login.repo.LoginRepo
import javax.inject.Inject

class LoginVM  @Inject constructor(var userLoginRepo: LoginRepo){
    var userLogin = userLoginRepo.userLogin
    var userLoginResponse = userLoginRepo.userLoginResponse

    fun loginUser(userLoginModel: LoginModel, context: Context){
        userLoginRepo.loginUser(userLoginModel,context)
        println("DATA VM ${userLoginModel.username},${userLoginModel.password}")
    }
}