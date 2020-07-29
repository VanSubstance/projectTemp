package com.example.contest

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class productElementAdapter(var productElementList: ArrayList<productElement>, val context: Context, var usage : Int, val productClick: (productElement) -> Unit) : RecyclerView.Adapter<productElementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productElementViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.product_seller_home, parent, false)
        when(usage) {
            // 판매자 이력
            2 -> {
                view = LayoutInflater.from(context).inflate(R.layout.product_seller_history, parent, false)
            }
            // 판매자 오늘 상품
            3 -> {
                view = LayoutInflater.from(context).inflate(R.layout.product_seller_home, parent, false)
            }
            // 구매자 재료
            4 -> {
                view = LayoutInflater.from(context).inflate(R.layout.product_buyer_market, parent, false)
            }
            // 구매자 완제품
            42 -> {
                view = LayoutInflater.from(context).inflate(R.layout.product_buyer_market, parent, false)
            }
            // 구매자 장바구니
            5 -> {
                view = LayoutInflater.from(context).inflate(R.layout.product_buyer_basket, parent, false)
            }
            // 판매자 날짜에 따른 이력
            22 -> {
                view = LayoutInflater.from(context).inflate(R.layout.seller_ui_history_date, parent, false)
            }
        }
        return productElementViewHolder(view, usage, productClick)
    }

    override fun getItemCount(): Int {
        return productElementList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: productElementViewHolder, position: Int) {

        holder.bind(productElementList[position], context)
    }

}