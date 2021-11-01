package com.projectassyifa.syifamilk.screen.product

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.category.adapter.AdapterCategory
import com.projectassyifa.syifamilk.data.category.vm.CategoryVM
import com.projectassyifa.syifamilk.data.product.model.ProductModel
import com.projectassyifa.syifamilk.data.product.vm.ProductVM
import com.projectassyifa.syifamilk.screen.alert.LoadingBrown
import kotlinx.android.synthetic.main.fragment_add_product_layout.*
import kotlinx.android.synthetic.main.fragment_adduser_layout.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import javax.inject.Inject


class AddProductLayout : DialogFragment() {
    private val cameraRequestId  = 1222
    var cat_id : String ? = null
    var dataLogin : SharedPreferences? = null

    @Inject
    lateinit var categoryVM: CategoryVM
    lateinit var adapterCategory: AdapterCategory

    @Inject
    lateinit var productVM: ProductVM

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
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        var rootView : View = inflater.inflate(R.layout.fragment_add_product_layout,container,false)

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

        select_photo.setOnClickListener {
            EasyImage.openGallery(this,1)
        }
        val username = dataLogin?.getString(
            getString(R.string.username),
            getString(R.string.default_value)
        )
        val spin = view.findViewById<Spinner>(R.id.pilih_kategori)
        //spinner category
        categoryVM.data_category?.observe(viewLifecycleOwner, Observer {
            adapterCategory = AdapterCategory(it,this.requireContext())
            spin.adapter = adapterCategory
            spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                   cat_id = it[position].category_id
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                   cat_id = it[0].category_id
                }
            }
        })
        categoryVM.category(this.requireContext())

        btn_save_product.setOnClickListener {

//            val loading = LoadingBrown(this.requireActivity())
//            loading.startLoading()
//            val handler = Handler()
//            handler.postDelayed(object :Runnable{
//                override fun run() {
//                    loading.isDismiss()
//                }
//
//            },3000)

            val dataProduct = ProductModel()

            dataProduct.product_name = productName.text.toString()
            dataProduct.product_price = productPrice.text.toString()
            dataProduct.product_description = productDesc.text.toString()
//            dataProduct.product_stock = productStock.text.toString()
            dataProduct.starting_price = productStartPrice.text.toString()
            dataProduct.product_category = cat_id.toString()
            dataProduct.created_by = username.toString()
//            println("DATA product_name ${dataProduct.product_name}")
//            println("DATA product_price ${dataProduct.product_price}")
//            println("DATA product_description ${dataProduct.product_description}")
//            println("DATA product_stock ${dataProduct.product_stock}")
//            println("DATA product_category ${dataProduct.product_category}")
            println("START PRICE ${dataProduct.starting_price}")

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
                if (productName.text.toString()=="" ||
                    productPrice.text.toString()=="" ||
                    productDesc.text.toString()==""
//                   productStock.text.toString()==""
                ){
                    Toast.makeText(this.context,"Data harus di isi semua", Toast.LENGTH_SHORT).show()
                }else{

//                    productVM.response_product?.observe(viewLifecycleOwner, Observer {
//                        if (it.status){
//                            startActivity(Intent(this.requireContext(),ManagProductActivity::class.java))
//                        }
//                    })

                    productVM.add_product(requireContext(),dataProduct,fileImage!!)
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
                nama_photo.setText(imageFile?.name)
                val requestOptions = RequestOptions().error(R.drawable.ic_ft)
                activity?.let {
                    Glide.with(it)
                        .load(imageFile)
                        .apply(requestOptions)
                        .into(product_photo)
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