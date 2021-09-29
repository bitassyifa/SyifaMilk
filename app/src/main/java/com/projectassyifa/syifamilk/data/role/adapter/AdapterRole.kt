package com.projectassyifa.syifamilk.data.role.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.projectassyifa.syifamilk.R
import com.projectassyifa.syifamilk.data.role.model.RoleModel

class AdapterRole (var dataSource : List<RoleModel>,val context: Context): BaseAdapter(){

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       val view : View
       val vh : ItemHolder
       if (convertView == null){
           view = inflater.inflate(R.layout.adapter_role,parent,false)
           vh = ItemHolder(view)
           view?.tag = vh
       } else {
           view = convertView
           vh = view.tag as ItemHolder
       }
        vh.role_nama.text = dataSource.get(position).role_nama

       return view
    }

    class ItemHolder (row : View?){
        val role_nama : TextView
        init {
                role_nama = row?.findViewById(R.id.role_name) as TextView
        }

    }
}


