package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.buyer_ui_market_ctgr_01.view.*

class buyerUIMarketCtgr01 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_market_ctgr_01, container, false)
        view.textMarketName.setText(currentCondition.marketTitle)
        view.buttonComplete.setOnClickListener {
            // 해당 시장 오늘 상품중 완제품만 보여준다.
            (activity as buyerUIMain).setBuyerFrag(24)
        }
        view.buttonRaw.setOnClickListener {
            (activity as buyerUIMain).setBuyerFrag(23)
        }
        return view
    }
}