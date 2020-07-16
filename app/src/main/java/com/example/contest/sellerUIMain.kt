package com.example.contest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.seller_ui_main.*
import java.text.SimpleDateFormat
import java.util.*

class sellerUIMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_ui_main)

        val currentTime = Calendar.getInstance().time
        var timeFormat = SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(currentTime)
        textTime.setText(timeFormat)


        //판매자 화면 전환
        setSellerFrag(11)

        sellerHome.setOnClickListener {
            setSellerFrag(11)
        }

        SellerHistory.setOnClickListener {
            setSellerFrag(21)
        }

        sellerToday.setOnClickListener {
            setSellerFrag(31)
        }

        sellerInfo.setOnClickListener {
            setSellerFrag(41)
        }
    }

    fun setSellerFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            11 -> {
                ft.replace(R.id.main_frame,sellerUIHome()).commit()
            }
            21 -> {
                ft.replace(R.id.main_frame,sellerUIHistory()).commit()
            }
            31 -> {
                ft.replace(R.id.main_frame,sellerUIToday()).commit()
            }
            41 -> {
                ft.replace(R.id.main_frame,sellerUIInfo()).commit()
            }
            12 -> {
                ft.replace(R.id.main_frame,sellerUIEnrollProduct()).commit()
            }
            42 -> {
                ft.replace(R.id.main_frame,sellerUIInfoModify()).commit()
            }
        }
    }

}
