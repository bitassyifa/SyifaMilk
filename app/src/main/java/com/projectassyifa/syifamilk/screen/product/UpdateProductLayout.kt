package com.projectassyifa.syifamilk.screen.product

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.category.adapter.AdapterCategory
import com.projectassyifa.syifamilk.data.category.vm.CategoryVM
import com.projectassyifa.syifamilk.data.product.model.ProductModel
import com.projectassyifa.syifamilk.data.product.vm.ProductVM
import com.projectassyifa.syifamilk.screen.user.ManagementUser
import kotlinx.android.synthetic.main.fragment_update_product_layout.*
import kotlinx.android.synthetic.main.fragment_update_user_layout.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import javax.inject.Inject


class UpdateProductLayout : DialogFragment() {
    var cat_id : String ? = null
    var dataLogin : SharedPreferences? = null
    private val cameraRequestId  = 1222

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

        var rootView : View = inflater.inflate(R.layout.fragment_update_product_layout,container,false)

//        val judul = rootView.findViewById<TextView>(R.id.title)
//        judul.setText(arguments?.getString("title"))
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = dataLogin?.getString(
            getString(R.string.username),
            getString(R.string.default_value)
        )

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

        val bundle = arguments

        val product_id = bundle!!.getString("product_id")
        u_productId.setText(product_id)

        val product_price = bundle!!.getString("product_price")
        u_productPrice.setText(product_price)

        val product_description = bundle!!.getString("product_description")
        u_productDesc.setText(product_description)

        val product_name = bundle!!.getString("product_name")
        u_productName.setText(product_name)

//        val product_stock = bundle!!.getString("product_stock")
//        u_productStock.setText(product_stock)

        val product_image = bundle!!.getString("product_image")
        nama_photo1.setText(product_image)

        var linkFoto= "http://202.62.9.138/syifamilk_api/uploads/foto_product/${bundle!!.getString("product_image")}"
        context?.let {
            Glide
                .with(it)
                .load(linkFoto)
                .fitCenter()
                .placeholder(R.drawable.ic_profil)
                .into(u_product_photo)
        };

        val spin = view.findViewById<Spinner>(R.id.pilih_kat)
        //spinner category
        categoryVM.data_category?.observe(viewLifecycleOwner, Observer {
            adapterCategory = AdapterCategory(it,this.requireContext())
            spin.adapter = adapterCategory
            var index = bundle!!.getString("product_category")?.toInt()?.minus(1)
            spin.setSelection(index!!)
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

        btn_save_u_product.setOnClickListener {
            val updateData = ProductModel()
            updateData.product_name = u_productName.text.toString()
            updateData.product_price = u_productPrice.text.toString()
            updateData.product_description = u_productDesc.text.toString()
//            updateData.product_stock = u_productStock.text.toString()
            updateData.product_category = cat_id.toString()
            updateData.updated_by = username!!


                println("FOTO $fileImage")
            println("DATA product_name $product_id")
            println("DATA product_name ${updateData.product_name}")
            println("DATA product_price ${updateData.product_price}")
            println("DATA product_stock ${updateData.product_stock}")
            println("DATA product_description ${updateData.product_description}")
            println("DATA product_category ${updateData.product_category}")
            println("DATA update by ${updateData.updated_by}")

            if (fileImage == null){
                productVM.update_product1(requireContext(),product_id!!,updateData)
                dismiss()
            } else {
                productVM.update_product(requireContext(), product_id!!, updateData, fileImage!!)
                dismiss()
            }
//            activity?.finish()
////                                aktivitas.finishMe()
//            activity?.startActivity(Intent(activity, ManagProductActivity::class.java));

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

                nama_photo1.setText(imageFile?.name)
                val requestOptions = RequestOptions().error(R.drawable.ic_ft)
                activity?.let {
                    Glide.with(it)
                        .load(imageFile)
                        .apply(requestOptions)
                        .into(u_product_photo)
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