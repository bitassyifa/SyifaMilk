package com.projectassyifa.syifamilk.data.user.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.projectassyifa.syifamilk.data.user.model.UserModel
import com.projectassyifa.syifamilk.data.user.repo.UserRepo
import java.io.File
import javax.inject.Inject

class UserVM @Inject constructor(val userRepo: UserRepo){
    val list_user : MutableLiveData<List<UserModel>>? = userRepo.list_user
    fun user(context: Context){
        userRepo.user(context)
    }
    fun delete_user(user_id : String,context: Context){
        userRepo.delete_user(user_id,context)
    }
    fun update_user(user_id : String,userModel: UserModel,file: File){
       userRepo.update_user(user_id,userModel,file)
        println("VM $user_id")
    }
    fun update_user1(user_id : String,userModel: UserModel){
        userRepo.update_user1(user_id,userModel)
        println("VM $user_id")
    }
}