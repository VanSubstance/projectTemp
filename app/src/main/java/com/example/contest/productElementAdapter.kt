package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class productElementAdapter(val productElementList: ArrayList<productElement>, val context: Context, var usage : Int) : RecyclerView.Adapter<productElementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productElementViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.product_seller_today, parent, false)
        when(usage) {
            1 -> {
                view = LayoutInflater.from(context).inflate(R.layout.product_seller_today, parent, false)
            }
            2 -> {
                view = LayoutInflater.from(context).inflate(R.layout.product_seller_history, parent, false)
            }
            3 -> {
                view = LayoutInflater.from(context).inflate(R.layout.product_seller_home, parent, false)
            }
        }
        return productElementViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productElementList.size
    }

    override fun onBindViewHolder(holder: productElementViewHolder, position: Int) {
        holder.bind(productElementList[position], context)
    }

}