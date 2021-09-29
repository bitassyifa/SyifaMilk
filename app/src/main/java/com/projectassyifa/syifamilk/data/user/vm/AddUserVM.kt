package com.projectassyifa.syifamilk.data.user.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import com.projectassyifa.syifamilk.data.user.model.UserModel
import com.projectassyifa.syifamilk.data.user.repo.AddUserRepo
import java.io.File
import javax.inject.Inject

class AddUserVM @Inject constructor(var addUserRepo: AddUserRepo):ViewModel(){
    fun add_user(context: Context, userModel: UserModel, file: File){
        addUserRepo.add_user(context,userModel,file)
    }
}