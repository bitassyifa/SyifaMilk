package com.projectassyifa.syifamilk.screen.ingredients

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.container.MyApp
import com.projectassyifa.syifamilk.data.ingredients.AdapterIngredients
import com.projectassyifa.syifamilk.data.ingredients.IngredientsVM
import com.projectassyifa.syifamilk.screen.product.AddProductLayout
import kotlinx.android.synthetic.main.activity_ingredients.*
import kotlinx.android.synthetic.main.fragment_report_product_sale.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class IngredientsActivity : AppCompatActivity() {
    var calender = Calendar.getInstance()

    val localeID = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

    @Inject
    lateinit var ingredientsVM: IngredientsVM
    lateinit var adapterIngredients: AdapterIngredients

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)
        (applicationContext as MyApp).applicationComponent.inject(this)
        refreshApp()

        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        tanggal_ingre.text = currentDate

        ingredientsVM.total?.observe(this, androidx.lifecycle.Observer {
            totalIngre.setText(formatRupiah.format(it.jumlah))
        })
        ingredientsVM.getTotalIngredients(this,currentDate)

        list_ingre.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        ingredientsVM.dataIngredients?.observe(this, androidx.lifecycle.Observer {
            adapterIngredients = AdapterIngredients(it,this)
            list_ingre.adapter = adapterIngredients
        })
        ingredientsVM.getIngredients(this,currentDate)

        addIngre.setOnClickListener {
            var dialog = AddIngredients()
            val bundle= Bundle()
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, "customDialog")
        }
        val dateIngre = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calender.set(Calendar.YEAR,year)
                calender.set(Calendar.MONTH,month)
                calender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                updateTgl()
            }
        }
        tanggal_ingre.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                DatePickerDialog(this@IngredientsActivity,
                    dateIngre,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    private fun updateTgl() {
        val formatTgl = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(formatTgl,Locale.US)
        tanggal_ingre.text = sdf.format(calender.time)

        ingredientsVM.total?.observe(this, androidx.lifecycle.Observer {
            totalIngre.setText(formatRupiah.format(it.jumlah))
        })
        ingredientsVM.getTotalIngredients(this,sdf.format(calender.time))

        list_ingre.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        ingredientsVM.dataIngredients?.observe(this, androidx.lifecycle.Observer {
            adapterIngredients = AdapterIngredients(it,this)
            list_ingre.adapter = adapterIngredients
        })
        ingredientsVM.getIngredients(this,sdf.format(calender.time))
    }

    private fun refreshApp() {
        reff.setOnRefreshListener {
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