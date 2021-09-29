package com.projectassyifa.syifamilk.data.category.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.data.category.model.CategoryModel
import com.projectassyifa.syifamilk.data.role.adapter.AdapterRole

class AdapterCategory (var dataSource : List<CategoryModel>,val context: Context) : BaseAdapter(){
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(p0: Int): Any {
        return dataSource[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View
        val vh : ItemContent

        if (convertView ==  null){
            view = inflater.inflate(R.layout.adapter_category,parent,false)
            vh = ItemContent(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemContent
        }
        vh.category_nama.text = dataSource.get(position).category_name

        return view
    }

    class ItemContent (row : View?){
        val category_nama : TextView
        init {
                category_nama = row?.findViewById(R.id.category_name) as TextView
        }
    }
}


