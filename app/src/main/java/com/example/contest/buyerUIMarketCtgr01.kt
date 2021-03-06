package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.buyer_ui_market_ctgr_01.view.*
import kotlinx.android.synthetic.main.seller_ui_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer

class buyerUIMarketCtgr01 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_market_ctgr_01, container, false)
        view.textMarketName.setText(currentCondition.marketTitle)

        view.buttonComplete.setOnClickListener {
            currentCondition.ctgr01 = "complete"
            (activity as buyerUIMain).setBuyerFrag(24)
        }
        view.buttonRaw.setOnClickListener {
            currentCondition.ctgr01 = "raw"
            (activity as buyerUIMain).setBuyerFrag(23)
        }
        return view
    }
    fun getCurrentTime(): String? {
        val time = System.currentTimeMillis()
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(time))
    }
}