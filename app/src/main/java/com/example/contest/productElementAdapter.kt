package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class productElementAdapter(val productElementList: ArrayList<productElement>, val context: Context) : RecyclerView.Adapter<productElementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productElementViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_element, parent, false)
        return productElementViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productElementList.size
    }

    override fun onBindViewHolder(holder: productElementViewHolder, position: Int) {
        holder.bind(productElementList[position], context)
    }

}