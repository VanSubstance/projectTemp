package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class locationAdapter (val context: Context, val m_List: ArrayList<locationInfo>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
       val view:View=LayoutInflater.from(context).inflate(R.layout.selectlocation_items,null)
        val m_item=view.findViewById<TextView>(R.id.mlist_item)
        val location=m_List[p0]
        m_item.text=location.mName

        return view

    }

    override fun getItem(p0: Int): Any {
        return m_List[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return m_List.size
    }

}