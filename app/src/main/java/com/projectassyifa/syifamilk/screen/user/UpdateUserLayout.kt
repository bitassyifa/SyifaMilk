package com.projectassyifa.syifamilk.screen.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.projectassyifa.syifamilk.R
import kotlinx.android.synthetic.main.fragment_update_user_layout.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.role.adapter.AdapterRole
import com.projectassyifa.syifamilk.data.role.vm.RoleVM
import com.projectassyifa.syifamilk.data.user.model.UserModel
import com.projectassyifa.syifamilk.data.user.vm.UserVM
import kotlinx.android.synthetic.main.adapter_user.*
import kotlinx.android.synthetic.main.fragment_adduser_layout.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import javax.inject.Inject


class UpdateUserLayout : DialogFragment() {

    private val cameraRequestId  = 1222

    @Inject
    lateinit var roleVM: RoleVM
    lateinit var adapterRole: AdapterRole

    @Inject
    lateinit var userVM: UserVM
//    private val aktivitas : ManagementUser = activity as ManagementUser
    var id_role : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        dialog?.getWindow()?.setLayout(700,500)
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        var rootView : View = inflater.inflate(R.layout.fragment_update_user_layout,container,false)

//        val judul = rootView.findViewById<TextView>(R.id.title)
//        judul.setText(arguments?.getString("title"))
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (getActivity()?.let {
                ContextCompat.checkSelfPermission(
                    it.getApplicationContext(), Manifest.permission.CAMERA
                )
            } == PackageManager.PERMISSION_DENIED)
            this.activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(Manifest.permission.CAMERA),
                    cameraRequestId
                )
            }

        val bundle = arguments

        val user_id = bundle!!.getString("user_id")
        val fullname = bundle!!.getString("fullname")
        val username = bundle!!.getString("username")
        val email = bundle!!.getString("email")
        val role_status = bundle!!.getString("role_status")
        val photo = bundle!!.getString("photo")
        val no_tlp = bundle!!.getString("no_tlp")

        user1.text = user_id
        fullname_user.setText(fullname)
        username_user.setText(username)
        email_user.setText(email)
//        role_user.setText(role_status)
        nama_photo.text =photo
        notlp_user.setText(no_tlp)
        var linkFoto= "http://202.62.9.138/syifamilk_api/uploads/foto_user/$photo"
        context?.let {
            Glide
                .with(it)
                .load(linkFoto)
                .fitCenter()
                .placeholder(R.drawable.ic_profil)
                .into(user_photo)
        };

        chose_file.setOnClickListener {
            EasyImage.openGallery(this,1)
        }
        val spinner =  view.findViewById<Spinner>(R.id.pilih_role)

        //list role
       roleVM.data_role?.observe(viewLifecycleOwner, Observer {
            adapterRole = AdapterRole(it,this.requireContext())
            spinner.adapter = adapterRole
           var index = bundle!!.getString("role_id")?.toInt()?.minus(1)
           spinner.setSelection(index!!)
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    id_role = it[position].role_id


                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    id_role = bundle.getString("role_id")!!.toInt()
                }
            }

        })

        roleVM.role()

        btn_update.setOnClickListener {
            val dataUpdate = UserModel()
            dataUpdate.username = username_user.text.toString()
            dataUpdate.fullname = fullname_user.text.toString()
            dataUpdate.email = email_user.text.toString()
            dataUpdate.no_tlp = notlp_user.text.toString()
            dataUpdate.role_id = id_role.toString()


            if (fileImage == null){
                println("UPDATE YES IMAGE $user_id , ${dataUpdate.username} ,${dataUpdate.fullname} ,${dataUpdate.email} ,${dataUpdate.no_tlp} ,${dataUpdate.role_id} ,$fileImage")
                userVM.update_user1(user_id!!,dataUpdate)
                activity?.finish()
//                                aktivitas.finishMe()
                activity?.startActivity(Intent(activity,ManagementUser::class.java));

            } else {
                println("UPDATE YES IMAGE $user_id , ${dataUpdate.username} ,${dataUpdate.fullname} ,${dataUpdate.email} ,${dataUpdate.no_tlp} ,${dataUpdate.role_id} ,$fileImage")
                    userVM.update_user(user_id!!,dataUpdate,fileImage!!)
                activity?.finish()
//                                aktivitas.finishMe()
                activity?.startActivity(Intent(activity,ManagementUser::class.java));

            }

        }
    }

    var fileImage : File? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("GAS")
//        val loading = UploadProgress(this)
//        loading.startLoading()
//        val handler = Handler()
//        handler.postDelayed(object :Runnable{
//            override fun run() {
//                loading.isDismiss()
//            }
//
//        },4000)


        EasyImage.handleActivityResult(requestCode,resultCode,data,this.activity,object :
            DefaultCallback(){
            var selectedImage = data?.data
            override fun onImagePicked(
                imageFile: File?,
                source: EasyImage.ImageSource?,
                type: Int
            ) {

                fileImage = imageFile
                var namaImage =imageFile?.name
                println("HASIL IMAGE $imageFile")

                nama_photo.setText(imageFile?.name)
                val requestOptions = RequestOptions().error(R.drawable.ic_ft)
                activity?.let {
                    Glide.with(it)
                        .load(imageFile)
                        .apply(requestOptions)
                        .into(user_photo)
                }
            }

        });
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
    }
}