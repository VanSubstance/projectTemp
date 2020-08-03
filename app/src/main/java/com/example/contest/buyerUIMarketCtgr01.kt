package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.buyer_ui_market_ctgr_01.*
import kotlinx.android.synthetic.main.buyer_ui_market_ctgr_01.textTime
import kotlinx.android.synthetic.main.buyer_ui_market_ctgr_01.view.*
import kotlinx.android.synthetic.main.seller_ui_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer

class buyerUIMarketCtgr01 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_market_ctgr_01, container, false)
        view.textMarketName.setText(currentCondition.marketTitle)
        var dataTime = "20:00" + ":00"
        var dataTimeList = dataTime.split(":")
        /**
        timer(period = 1000) {
            var currentTime : Long = System.currentTimeMillis()
        }
        */

        view.buttonComplete.setOnClickListener {
            // 해당 시장 오늘 상품중 완제품만 보여준다.
            (activity as buyerUIMain).setBuyerFrag(24)
        }
        view.buttonRaw.setOnClickListener {
            (activity as buyerUIMain).setBuyerFrag(23)
        }
        return view
    }
    fun getCurrentTime(): String? {
        val time = System.currentTimeMillis()
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(time))
    }
}