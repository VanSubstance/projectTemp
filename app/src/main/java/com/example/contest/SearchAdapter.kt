package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by Administrator on 2017-08-07.
 */
class SearchAdapter(private val list: List<String>, private val context: Context) : BaseAdapter() {
    private val inflate: LayoutInflater
    private var viewHolder: ViewHolder? = null
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(i: Int): Any {
        return 0
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View, viewGroup: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.row_listview, null)
            viewHolder = ViewHolder()
            viewHolder!!.label = convertView.findViewById<View>(R.id.label) as TextView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder!!.label!!.text = list[position]
        return convertView
    }

    internal inner class ViewHolder {
        var label: TextView? = null
    }

    init {
        inflate = LayoutInflater.from(context)
    }
}