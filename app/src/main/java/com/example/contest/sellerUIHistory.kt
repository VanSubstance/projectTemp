package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.seller_ui_history.*

class sellerUIHistory : Fragment() {

    private lateinit var historyElementList: ArrayList<sellerUIHistoryDate>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var adapter: sellerUIHistoryAdapter


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_history, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerView.layoutManager = linearLayoutManager

        historyElementList = ArrayList()


        for (i in sampledb.productMap) {
            val element = sellerUIHistoryDate()
            element.setData(i.key, i.value as ArrayList<productElement>)
            historyElementList.add(element)
        }
        adapter = sellerUIHistoryAdapter(historyElementList, requireContext(), 1)
        RecyclerView.adapter = adapter

    }
}