package com.projectassyifa.syifamilk.data.user.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.user.model.UserModel
import com.projectassyifa.syifamilk.data.user.vm.UserVM
import com.projectassyifa.syifamilk.screen.login.LoginActivity
import com.projectassyifa.syifamilk.screen.user.AdduserLayout
import com.projectassyifa.syifamilk.screen.user.ManagementUser
import com.projectassyifa.syifamilk.screen.user.UpdateUserLayout
import kotlinx.android.synthetic.main.activity_order.*
import javax.inject.Inject

class AdapterUser(val listUser: List<UserModel>, var activity: Activity)
    :RecyclerView.Adapter<UserVH>(){

    @Inject
    lateinit var userVM: UserVM
    private val aktivitas : ManagementUser = activity as ManagementUser
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user,parent,false)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)


        return UserVH(view)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        println("DATA ADAPTER ${listUser[position].email}")
       holder.id.text = listUser[position].user_id
        holder.nama.text = listUser[position].fullname
        holder.role.text = listUser[position].role_status
        holder.email.text = listUser[position].email



//        var linkFoto= "http://202.62.9.138/syifamilk_api/uploads/foto_user/${listUser[position].photo}"
//        Glide
//            .with(activity)
//            .load(linkFoto)
//            .fitCenter()
//            .placeholder(R.drawable.ic_profil)
//            .into(holder.photo);

        holder.delete.setOnClickListener {
            // Initialize a new instance of
            val builder = AlertDialog.Builder(activity)

            // Set the alert dialog title
            builder.setTitle("Konfirmasi Delete User")

            // Display a message on alert dialog
            builder.setMessage("Hapus data ${listUser[position].fullname}?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Ya") { dialog, which ->
                // Do something when user press the positive button
              userVM.delete_user(listUser[position].user_id,activity)
                    aktivitas.finishMe()
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

        holder.edit.setOnClickListener {
            // Initialize a new instance of
            val builder = AlertDialog.Builder(activity)

            // Set the alert dialog title
            builder.setTitle("Konfirmasi Update User")

            // Display a message on alert dialog
            builder.setMessage("Update data ${listUser[position].fullname}?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Ya") { dialog, which ->
                // Do something when user press the positive button
                var dialog = UpdateUserLayout()
                val bundle= Bundle()
                dialog.arguments = bundle

                bundle.putString("user_id",listUser[position].user_id)
                bundle.putString("fullname",listUser[position].fullname)
                bundle.putString("email",listUser[position].email)
                bundle.putString("role_status",listUser[position].role_status)
                bundle.putString("role_id",listUser[position].role_id)
                bundle.putString("photo",listUser[position].photo)
                bundle.putString("username",listUser[position].username)
                bundle.putString("no_tlp",listUser[position].no_tlp)
                dialog.show(aktivitas.supportFragmentManager, "customDialog")
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

    override fun getItemCount(): Int {
        return listUser.count()
    }

}

class UserVH (view: View) : RecyclerView.ViewHolder(view){
    var id = view.findViewById<TextView>(R.id.id_user)
    var nama = view.findViewById<TextView>(R.id.nama)
    var role = view.findViewById<TextView>(R.id.role)
    var email = view.findViewById<TextView>(R.id.email)
    var edit = view.findViewById<ImageButton>(R.id.btn_edit_user)
    var delete = view.findViewById<ImageButton>(R.id.btn_delete_user)
//    var photo = view.findViewById<ImageView>(R.id.photo)
}
