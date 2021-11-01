package com.projectassyifa.syifamilk.screen.ingredients

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.ingredients.IngredientsVM
import kotlinx.android.synthetic.main.fragment_update_ingredients.*
import javax.inject.Inject

import android.widget.TextView
import com.projectassyifa.syifamilk.data.ingredients.IngredientsModel
import java.util.*


class UpdateIngredients : DialogFragment() {
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
        var rootView : View = inflater.inflate(R.layout.fragment_update_ingredients,container,false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val username = dataLogin?.getString(
            getString(R.string.username),
            getString(R.string.default_value)
        )
        val id_ingredients = bundle!!.getString("id_ingredients")
        val name_ingredients = bundle!!.getString("name_ingredients")
        val qty = bundle!!.getString("qty")
        val price = bundle!!.getString("price")


        u_ingreId.text = id_ingredients
        u_ingreName.setText(name_ingredients)
        u_ingreQty.setText(qty)
        u_ingrePrice.setText(price)

        btnEditIngre.setOnClickListener {
            val dataIngredients = IngredientsModel()
            dataIngredients.name_ingredients = u_ingreName.text.toString()
            dataIngredients.qty = u_ingreQty.text.toString()
            dataIngredients.price = u_ingrePrice.text.toString().toInt()
            dataIngredients.created_by = username.toString()
            ingredientsVM.responseAPI?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if (it.status){
                    dismiss()
                }
            })
            ingredientsVM.updateIngredients(this.requireContext(),id_ingredients!!,dataIngredients)
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