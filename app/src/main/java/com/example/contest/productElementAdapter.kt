package com.example.contest

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.product_buyer_market.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

class productElementAdapter(
    var productElementList: ArrayList<productElement>,
    val context: Context,
    var usage: Int,
    val productClick: (productElement) -> Unit
) : RecyclerView.Adapter<productElementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productElementViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.product_seller_home, parent, false)
        when (usage) {
            // 판매자 이력
            2 -> {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.product_seller_history, parent, false)
            }
            // 판매자 오늘 상품
            3 -> {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.product_seller_home, parent, false)
            }
            // 구매자 재료
            4 -> {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.product_buyer_market, parent, false)
            }
            // 구매자 완제품
            42 -> {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.product_buyer_market, parent, false)
            }
            // 구매자 장바구니
            5 -> {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.product_buyer_basket, parent, false)
            }
            // 판매자 날짜에 따른 이력
            22 -> {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.seller_ui_history_date, parent, false)
            }
        }
        return productElementViewHolder(view, usage, productClick)
    }

    override fun getItemCount(): Int {
        return productElementList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: productElementViewHolder, position: Int) {

        // 타이머
        val currentTime = Calendar.getInstance().time
        val endDateDay = LocalDate.now().toString() + " " + userInfo.timeClose.toString() + ":00"
        val format1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.getDefault())
        val endDate = format1.parse(endDateDay)
        // 차이
        var tick = endDate.time - currentTime.time
        holder.CountDownTimer = object : CountDownTimer(tick, 1000) {
            override fun onFinish() {
                holder.textCloseTime.setText("마감")
            }
            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val Hours = diff / hoursInMilli
                diff %= hoursInMilli
                val Minutes = diff / minutesInMilli
                diff %= minutesInMilli
                val Seconds = diff / secondsInMilli
                holder.textCloseTime.setText(Hours.toString() + ":" + Minutes.toString() + ":" + Seconds.toString())
            }

        }.start()

        holder.bind(productElementList[position], context)
    }

}