package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.seller_ui_today.*

class sellerUIToday : Fragment() {

    private lateinit var productElementList: ArrayList<productElement>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var adapter: productElementAdapter

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_today, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerView.layoutManager = linearLayoutManager

        productElementList = ArrayList()

        // 데이터베이스에서 조건에 맞는 상품들 불러오기
        // 상품들 productElement 양식에 맞춰서 데이터 집어넣기
        // productElementList에 넣어주기
        for (i in 0 until 7) {
            val element = productElement("Test_$i")
            productElementList.add(element)
        }
        adapter = productElementAdapter(productElementList, requireContext(), 1) {
            productElement ->
            // Today 폐쇄함
        }
        RecyclerView.adapter = adapter

    }

}