package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class sellerUIHistoryAdapter(var historyElementListList: MutableMap<String, ArrayList<productElement>>, val context: Context, var usage : Int) : RecyclerView.Adapter<sellerUIHistoryAdapter.sellerUIHistoryViewHolder>() {

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
        holder.bind((historyElementListList.values.toList())[position]
            , (historyElementListList.keys.toList())[position], context)
    }

    inner class sellerUIHistoryViewHolder(elementView: View) : RecyclerView.ViewHolder(elementView) {
        val textDate = elementView.findViewById<TextView>(R.id.staticDate)


        fun bind(historyElements: ArrayList<productElement>, date : String, context: Context) {
            textDate.setText(date)

            val s = itemView.findViewById<RecyclerView>(R.id.RecyclerView11)
            val sAdapter = productElementAdapter(historyElements, context, 2) {
                productElement ->
            }
            s.adapter = sAdapter
            val layoutManager = LinearLayoutManager(context)
            s.layoutManager = layoutManager
            s.setHasFixedSize(true)

        }
    }

}