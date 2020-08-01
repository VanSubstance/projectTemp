package com.example.contest

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.seller_ui_main.*
import kotlinx.android.synthetic.main.seller_ui_main.textTime
import java.text.SimpleDateFormat
import java.util.*

class sellerUIMain : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_ui_main)

        Thread(Runnable {
            while (!Thread.interrupted()) try {
                Thread.sleep(1000)
                runOnUiThread { textTime.setText(getCurrentTime()) }
            } catch (e: InterruptedException) {
            }
        }).start()

        //판매자 화면 전환
        setSellerFrag(11)

        sellerHome.setOnClickListener {
            setSellerFrag(11)
        }

        SellerHistory.setOnClickListener {
            setSellerFrag(21)
        }

        sellerInfo.setOnClickListener {
            setSellerFrag(41)
        }
    }

    fun getCurrentTime(): String? {
        val time = System.currentTimeMillis()
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(time))
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
            41 -> {
                ft.replace(R.id.main_frame,sellerUIInfo()).commit()
            }
            12 -> {
                ft.replace(R.id.main_frame,sellerUIEnrollProduct()).commit()
            }
            13 -> {
                ft.replace(R.id.main_frame,sellerUIHomeProductSpecific()).commit()
            }
            14 -> {
                ft.replace(R.id.main_frame,sellerUIHomeProductModify()).commit()
            }
            42 -> {
                ft.replace(R.id.main_frame,sellerUIInfoModify()).commit()
            }
        }
    }

    // 상품의 세부사항을 보여주는 함수
    fun showProductSpecific(productElement: productElement, usage : Int) {
        when (usage) {
            // 판매자 오늘 상품
            1 -> {
                var frag = sellerUIHomeProductSpecific()
                currentProductElement.currentProductElement = productElement
                setSellerFrag(13)
            }
        }
    }

    // 뒤로가기 테스트
    private final var FINISH_INTERVAL_TIME: Long = 2000
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0) {
            var tempTime = System.currentTimeMillis()
            var intervalTime = tempTime - backPressedTime.flag
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                finishAffinity()
                System.exit(0)
                super.onBackPressed()
            } else {
                backPressedTime.flag = tempTime;
                startActivity(Intent(this, sellerUIMain::class.java))
                Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }
        super.onBackPressed()
    }
}
