package com.projectassyifa.syifamilk.screen.user

import android.Manifest
import android.content.Intent
import android.content.Intent.getIntent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.cart.vm.CartVM
import com.projectassyifa.syifamilk.data.user.model.UserModel
import kotlinx.android.synthetic.main.fragment_adduser_layout.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
//import android.R
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.projectassyifa.syifamilk.data.user.vm.AddUserVM
import com.projectassyifa.syifamilk.screen.home.HomeActivity
import com.projectassyifa.syifamilk.screen.oder.OrderActivity
import javax.inject.Inject
import android.app.Activity
import com.projectassyifa.syifamilk.screen.alert.Successfully


class AdduserLayout : DialogFragment() {
    private val cameraRequestId  = 1222
//    private val aktivitas : ManagementUser = activity as ManagementUser
    @Inject
    lateinit var addUserVM : AddUserVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).applicationComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        var rootView : View = inflater.inflate(R.layout.fragment_adduser_layout,container,false)

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

//        // Step 1: designate a style
//        // Step 1: designate a style
//        val mAwesomeValidation = AwesomeValidation(ValidationStyle.BASIC)
//
//        // to validate the confirmation of another field
//        // to validate the confirmation of another field
//        val regexPassword =
//            "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}"
//        mAwesomeValidation.addValidation(
//            activity,
//            com.projectassyifa.syifamilk.R.id.password_input,
//            regexPassword,
//            com.projectassyifa.syifamilk.R.string.err_password
//        )
//// to validate a confirmation field (don't validate any rule other than confirmation on confirmation field)
//        mAwesomeValidation.addValidation(
//            activity,
//            com.projectassyifa.syifamilk.R.id.confpassword_input,
//            com.projectassyifa.syifamilk.R.id.password_input,
//            com.projectassyifa.syifamilk.R.string.err_password_confirmation
//        )


        pilih_file.setOnClickListener {
           EasyImage.openGallery(this,1)
        }

        btn_save.setOnClickListener {
//            mAwesomeValidation.validate();
            val dataUser = UserModel()

            dataUser.username = username_input.text.toString()
            dataUser.fullname = fullname_input.text.toString()
            dataUser.email = email_input.text.toString()
            dataUser.no_tlp = notlp_input.text.toString()
            dataUser.password = password_input.text.toString()

            if (fileImage == null) {
//                val loading = FileNull(this)
//                loading.startLoading()
//                val handler = Handler()
//                handler.postDelayed(object :Runnable{
//                    override fun run() {
//                        loading.isDismiss()
//                    }
//
//                },4000)
                Toast.makeText(
                    this.context,
                    "Mohon upload foto !!",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                if (username_input.text.toString()=="" ||
                    fullname_input.text.toString()=="" ||
                    email_input.text.toString()=="" ||
                    notlp_input.text.toString()=="" ||
                    password_input.text.toString()=="" ||
                    confpassword_input.text.toString()==""
                ){
                    Toast.makeText(this.context,"Data harus di isi semua",Toast.LENGTH_SHORT).show()
                }else if (password_input.text.toString() != confpassword_input.text.toString()){
                    Toast.makeText(this.context,"Password tidak sesuai", Toast.LENGTH_SHORT).show()
                }else{
                   addUserVM.add_user(requireContext(),dataUser, fileImage!!)
//                    startActivity(Intent(this.context,ManagementUser::class.java))

                    dismiss()


                        //show dialog


//                    activity?.startActivity(Intent(activity,ManagementUser::class.java));
//                    aktivitas.finishMe()
                }
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
                println("HASIL IMAGE $fileImage")
                println("NAMA IMAGE $namaImage")
                nama_file.setText(imageFile?.name)
                val requestOptions = RequestOptions().error(R.drawable.ic_ft)
                activity?.let {
                    Glide.with(it)
                        .load(imageFile)
                        .apply(requestOptions)
                        .into(result_image)
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