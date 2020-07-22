package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.seller_ui_history_date.*

class sellerUIHistoryAdapter(var productElementList: ArrayList<sellerUIHistoryDate>, val context: Context, var usage : Int) : RecyclerView.Adapter<sellerUIHistoryAdapter.sellerUIHistoryViewHolder>() {

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
        return productElementList.size
    }

    override fun onBindViewHolder(holder: sellerUIHistoryViewHolder, position: Int) {

        holder.bind(productElementList[position], context)
    }

    inner class sellerUIHistoryViewHolder(elementView: View) : RecyclerView.ViewHolder(elementView) {
        val textDate = elementView.findViewById<TextView>(R.id.textDate)


        fun bind(productElements: sellerUIHistoryDate, context: Context) {

            val currentTime = Calendar.getInstance().time
            var timeFormat = SimpleDateFormat("yyyy-mm-dd", Locale.KOREA).format(currentTime)
            textDate.setText(timeFormat)

            var productElementList1: ArrayList<productElement>
            productElementList1 = ArrayList()
            for (i in 0 until 4) {
                val element = productElement("Test_$i")
                productElementList1.add(element)
            }

            val s = itemView.findViewById<RecyclerView>(R.id.RecyclerView11)
            val sAdapter = productElementAdapter(productElementList1, context, 2) {}
            s.adapter = sAdapter
            val layoutManager = LinearLayoutManager(context)
            s.layoutManager = layoutManager
            s.setHasFixedSize(true)

        }
    }

}