package com.example.contest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.buyer_ui_main.*
import kotlinx.android.synthetic.main.buyer_ui_main.textTime
import kotlinx.android.synthetic.main.seller_ui_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class buyerUIMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer_ui_main)

        val currentTime = LocalDate.now()
        textTime.setText(currentTime.toString())

        // 구매자 화면 전환
        setBuyerFrag(11)

        buyerHome.setOnClickListener {
            setBuyerFrag(11)
        }

        buyerToday.setOnClickListener {
            setBuyerFrag(21)
        }

        buyerInfo.setOnClickListener {
            setBuyerFrag(31)
        }
    }

    fun setBuyerFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum)
        {
            11 -> {
                ft.replace(R.id.main_frame,buyerUIHome()).commit()
            }
            21 -> {
                ft.replace(R.id.main_frame,buyerUIToday()).commit()
            }
            31 -> {
                ft.replace(R.id.main_frame,buyerUIInfo()).commit()
            }
            32 -> {
                ft.replace(R.id.main_frame,buyerUIInfoModify()).commit()
            }
            12 -> {
                ft.replace(R.id.home_frame,recipeUIMeat()).commit()
            }
            13 -> {
                ft.replace(R.id.home_frame,recipeUISeafood()).commit()
            }
            14 -> {
                ft.replace(R.id.home_frame,recipeUIVegetable()).commit()
            }
            15 -> {
                ft.replace(R.id.home_frame,recipeUIEtc()).commit()
            }
        }


    }

}
