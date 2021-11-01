package com.projectassyifa.syifamilk.screen.ingredients

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.ingredients.IngredientsModel
import com.projectassyifa.syifamilk.data.ingredients.IngredientsVM
import com.projectassyifa.syifamilk.screen.oder.OrderActivity
import kotlinx.android.synthetic.main.fragment_add_ingredients.*
import javax.inject.Inject


class AddIngredients : DialogFragment() {
    var dataLogin : SharedPreferences? = null
    @Inject
    lateinit var ingredientsVM: IngredientsVM


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
        var rootView : View = inflater.inflate(R.layout.fragment_add_ingredients,container,false)
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = dataLogin?.getString(
            getString(R.string.username),
            getString(R.string.default_value)
        )
        btnSaveIngre.setOnClickListener {
            var dataIngre = IngredientsModel()
            dataIngre.name_ingredients = ingreName.text.toString()
            dataIngre.qty = ingreQty.text.toString()
            dataIngre.price = ingrePrice.value.toInt()
            dataIngre.created_by = username.toString()
            ingredientsVM.responseAPI?.observe(viewLifecycleOwner, Observer {
                if (it.status){
                   dismiss()
                }
            })
            ingredientsVM.postIngredients(requireContext(),dataIngre)
        }

    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
    }

}