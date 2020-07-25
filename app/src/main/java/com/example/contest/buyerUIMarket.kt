package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.buyer_ui_market.*

class buyerUIMarket : Fragment() {

    private lateinit var productElementList: ArrayList<productElement>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var adapter: productElementAdapter

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_market, container, false)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerView.layoutManager = linearLayoutManager

        productElementList = ArrayList()

        productElementList = conditionData.productList

        adapter = productElementAdapter(productElementList, requireContext(), 4) {
                productElement ->
            // 팝업창 띄우기
            (activity as buyerUIMain).showProductSpecific(productElement, 1)
        }
        RecyclerView.adapter = adapter

    }

}