package com.projectassyifa.syifamilk.screen.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.login.model.LoginModel
import com.projectassyifa.syifamilk.data.login.vm.LoginVM
import com.projectassyifa.syifamilk.screen.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_login_layout.*
import javax.inject.Inject


class LoginLayout : Fragment(),View.OnClickListener {
    var dataLogin : SharedPreferences? = null
    lateinit var navController : NavController

    @Inject
    lateinit var loginVM: LoginVM



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)
        dataLogin = activity?.getSharedPreferences(
            getString(R.string.datasave),
            Context.MODE_PRIVATE
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if (dataLogin?.contains(getString(R.string.username))!! && dataLogin?.contains(getString(R.string.login_method_key))!!)
        {
            //jika ada sesion langsung ke home
            view.findNavController().navigate(R.id.global_to_homeActivity)
        }
        userLoginButton.setOnClickListener(this)
        navController = Navigation.findNavController(view)

        loginVM.userLogin.observe(viewLifecycleOwner, Observer {
            if (it.status != true) {
                Toast.makeText(
                    this.context,
                    "Username or password salah",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this.context, "Login Success", Toast.LENGTH_SHORT).show()
                loginVM.userLoginResponse.observe(viewLifecycleOwner, Observer {
                    if (it != null){
                        with(dataLogin?.edit()) {
                            this?.putString(getString(R.string.username), it.username)
                            this?.putString(getString(R.string.id_pegawai), it.user_id)
                            this?.putString(getString(R.string.nama_pegawai), it.fullname)
                            this?.putString(getString(R.string.no_tlp), it.no_tlp)
//                            this?.putString(getString(R.string.role_status), it.role_status)
                            this?.putString(getString(R.string.email), it.email)
                            this?.putString(getString(R.string.role), it.role)
                            this?.putString(getString(R.string.photo_user), it.photo)
                            this?.putString(
                                getString(R.string.login_method_key),
                                "appLogin"
                            )
                            this?.commit()
                        }
                        navController.navigate(R.id.global_to_homeActivity)
                    }
                })
            }
        })
    }

    override fun onClick(v: View?) {
        when(v) {
            userLoginButton -> {
                val userLoginModel = LoginModel(
                    username = usernameInput.text.toString(),
                    password=  userPasswordInput.text.toString()
                )
                if (usernameInput.toString() == ""  && userPasswordInput.text.toString() == ""
                ){
                    Toast.makeText(this.context, "Isi semua form", Toast.LENGTH_SHORT).show()
                } else {
                    println("INI DATA ${userLoginModel.username},${userLoginModel.password}")
                    loginVM.loginUser(userLoginModel, requireContext())
                }
            }
        }
    }

}