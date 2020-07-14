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
        setSellerFrag(0)

        sellerHome.setOnClickListener {
            setSellerFrag(0)
        }

        SellerHistory.setOnClickListener {
            setSellerFrag(1)
        }

        sellerToday.setOnClickListener {
            setSellerFrag(2)
        }

        sellerInfo.setOnClickListener {
            setSellerFrag(3)
        }
    }

    private fun setSellerFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            0 -> {
                ft.replace(R.id.main_frame,sellerUIHome()).commit()
            }
            1 -> {
                ft.replace(R.id.main_frame,sellerUIHistory()).commit()
            }
            2 -> {
                ft.replace(R.id.main_frame,sellerUIToday()).commit()
            }
            3 -> {
                ft.replace(R.id.main_frame,sellerUIInfo()).commit()
            }
        }


    }

}
