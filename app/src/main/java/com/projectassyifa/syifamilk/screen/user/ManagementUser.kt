package com.projectassyifa.syifamilk.screen.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.user.adapter.AdapterUser
import com.projectassyifa.syifamilk.data.user.vm.UserVM
import com.projectassyifa.syifamilk.screen.alert.LoadingBrown
import kotlinx.android.synthetic.main.activity_manag_product.*
import kotlinx.android.synthetic.main.activity_management_user.*
import kotlinx.android.synthetic.main.activity_management_user.refresh
import javax.inject.Inject

class ManagementUser : AppCompatActivity() {

    @Inject
    lateinit var userVM: UserVM
    lateinit var adapterUser: AdapterUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_management_user)
        (applicationContext as MyApp).applicationComponent.inject(this)

        refreshApp()
//        val loading = LoadingBrown(this)
//        loading.startLoading()
//        val handler = Handler()
//        handler.postDelayed(object :Runnable{
//            override fun run() {
//                loading.isDismiss()
//            }
//
//        },3000)
        list_user.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        userVM.list_user?.observe(this, Observer {
            adapterUser = AdapterUser(it,this)
            list_user.adapter =adapterUser
        })
        userVM.user(this)

        btn_add_user.setOnClickListener {
            var dialog = AdduserLayout()
            val bundle= Bundle()
            dialog.arguments = bundle


            dialog.show(supportFragmentManager, "customDialog")
        }

    }

    private fun refreshApp() {
        refresh.setOnRefreshListener {
            Toast.makeText(this,"Refresh", Toast.LENGTH_SHORT).show()
            finish();
            startActivity(getIntent());
        }
    }

    fun finishMe() {
        finish()
        startActivity(getIntent());
    }
}