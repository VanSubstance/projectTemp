package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_seller_home_specific.view.*
import kotlin.collections.ArrayList

class sellerUIHistoryAdapter(var historyElementListList: ArrayList<sellerUIHistoryDate>, val context: Context, var usage : Int) : RecyclerView.Adapter<sellerUIHistoryAdapter.sellerUIHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): sellerUIHistoryViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.seller_ui_history_date, parent, false)
        when(usage) {
            1 -> {
                view = LayoutInflater.from(context).inflate(R.layout.seller_ui_history_date, parent, false)
            }
        }
        return sellerUIHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyElementListList.size
    }

    override fun onBindViewHolder(holder: sellerUIHistoryViewHolder, position: Int) {
        holder.bind(historyElementListList[position], context)
    }

    inner class sellerUIHistoryViewHolder(elementView: View) : RecyclerView.ViewHolder(elementView) {
        val textDate = elementView.findViewById<TextView>(R.id.textDate)


        fun bind(historyElements: sellerUIHistoryDate, context: Context) {

            textDate.setText(historyElements.date)

            val s = itemView.findViewById<RecyclerView>(R.id.RecyclerView11)
            val sAdapter = productElementAdapter(historyElements.productList, context, 2) {
                productElement ->
                // 상품 정보가 떠야함 팝업으로
                val view = LayoutInflater.from(context).inflate(R.layout.product_seller_history_specific, null)
                view.textTitle.text = productElement.title
                view.textPrice.text = productElement.price.toString()
                view.textQuan.text = productElement.quantity.toString()
                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("상품 정보")
                    .create()
                alertDialog.setView(view)
                alertDialog.show()
            }
            s.adapter = sAdapter
            val layoutManager = LinearLayoutManager(context)
            s.layoutManager = layoutManager
            s.setHasFixedSize(true)

        }
    }

}