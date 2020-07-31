package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.buyer_ui_market_selection.view.*

class buyerUIMarketSelection : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_market_selection, container, false)
        view.buttonMarket01.setOnClickListener {
            currentCondition.marketTitle = view.buttonMarket01.text.toString()
            (activity as buyerUIMain).setBuyerFrag(22)
        }
        view.buttonMarket02.setOnClickListener {
            currentCondition.marketTitle = view.buttonMarket02.text.toString()
            (activity as buyerUIMain).setBuyerFrag(22)
        }

        return view
    }
}