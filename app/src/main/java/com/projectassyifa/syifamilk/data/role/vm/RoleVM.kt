package com.projectassyifa.syifamilk.data.role.vm

import androidx.lifecycle.MutableLiveData
import com.projectassyifa.syifamilk.data.role.model.RoleModel
import com.projectassyifa.syifamilk.data.role.repo.RoleRepo
import javax.inject.Inject

class RoleVM @Inject constructor(val roleRepo: RoleRepo){
    val data_role : MutableLiveData<List<RoleModel>>? = roleRepo.data_role

    fun role(){
        roleRepo.role()
    }
}