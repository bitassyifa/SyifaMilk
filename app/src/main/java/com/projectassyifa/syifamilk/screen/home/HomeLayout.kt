package com.projectassyifa.syifamilk.screen.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.screen.dashboard.DashboardActivity
import com.projectassyifa.syifamilk.screen.ingredients.IngredientsActivity
import com.projectassyifa.syifamilk.screen.login.LoginActivity
import com.projectassyifa.syifamilk.screen.oder.OrderActivity
import com.projectassyifa.syifamilk.screen.product.ManagProductActivity
import com.projectassyifa.syifamilk.screen.report.ReportMainActivity
import com.projectassyifa.syifamilk.screen.user.ManagementUser
import kotlinx.android.synthetic.main.fragment_home_layout.*

class HomeLayout : Fragment() {
    var dataLogin: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        return inflater.inflate(R.layout.fragment_home_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val role = dataLogin?.getString(
            getString(R.string.role),
            getString(R.string.default_value)
        )

        menu_manaj_user.setOnClickListener {
            if (role == "2"){
                menu_manaj_user.isActivated = false
                Toast.makeText(this.context,"Hanya admin yang bisa Manage User", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this.context, ManagementUser::class.java))
            }
        }
        menu_transaksi.setOnClickListener {
            startActivity(Intent(this.context,OrderActivity::class.java))
        }
        menu_inventory.setOnClickListener {
            startActivity(Intent(this.context,ManagProductActivity::class.java))
        }
        menu_report.setOnClickListener {
            startActivity(Intent(this.context,ReportMainActivity::class.java))
        }
//
        menu_dashboard.setOnClickListener {
            startActivity(Intent(this.context,DashboardActivity::class.java))
        }
        menu_ingredients.setOnClickListener {
            startActivity(Intent(this.context,IngredientsActivity::class.java))
        }
//
        btn_logout.setOnClickListener {
            println("BTN LOGOUT")
            // Initialize a new instance of
            val builder = AlertDialog.Builder(activity)

            // Set the alert dialog title
            builder.setTitle("Konfirmasi Logout")

            // Display a message on alert dialog
            builder.setMessage("Apakah anda yakin, Anda ingin Keluar Akun sekarang?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Ya") { dialog, which ->
                // Do something when user press the positive button
                with(dataLogin?.edit()) {
                    this?.clear()
                    this?.apply()
                    Intent(getContext(), LoginActivity::class.java).apply {
                        addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.also { startActivity(it) }
                }
            }


// Display a negative button on alert dialog
            builder.setNegativeButton("Tidak"){dialog,which ->
//                    Toast.makeText(activity,"Anda tidak yakin.", Toast.LENGTH_SHORT).show()
            }


// Display a neutral button on alert dialog
//                        builder.setNeutralButton("Cancel"){_,_ ->
//                            Toast.makeText(activity,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
//                        }

// Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

// Display the alert dialog on app interface
            dialog.show()
        }

    }
}