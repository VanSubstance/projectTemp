package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class productElementAdapter(var productElementList: ArrayList<productElement>, val context: Context, var usage : Int, val productClick: (productElement) -> Unit) : RecyclerView.Adapter<productElementViewHolder>() {

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
            4 -> {
                view = LayoutInflater.from(context).inflate(R.layout.product_buyer_today, parent, false)
            }
            22 -> {
                view = LayoutInflater.from(context).inflate(R.layout.seller_ui_history_date, parent, false)
            }
        }
        return productElementViewHolder(view, productClick)
    }

    override fun getItemCount(): Int {
        return productElementList.size
    }

    override fun onBindViewHolder(holder: productElementViewHolder, position: Int) {

        holder.bind(productElementList[position], context)
    }

}