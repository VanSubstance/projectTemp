package com.example.contest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread(Runnable {
            while (!Thread.interrupted()) try {
                Thread.sleep(1000)
                runOnUiThread {
                    if (SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()) == "00:00:00") {
                        pushProducts()
                    }
                }
            } catch (e: InterruptedException) {
            }
        }).start()

        buttonLogin.setOnClickListener {
                login(textUserID.text.toString(), textUserPW.text.toString())
        }
        buttonSignUp.setOnClickListener{
            val SignUp_user=Intent(this,signup_sellect::class.java)
            startActivity(SignUp_user)
        }
        

    }

    private fun login(id: String, pw: String) {
        var err : Int = 0
        userInfo.id = id
        userInfo.pw = pw
        // 소비자 -> startActivity(buyerUI)
        // 판매자 -> startActivity(sellerUI)
    }
}

private fun pushProducts() {
    // 오늘 자 데이터를 기록으로 밀어주기
}
// 앱 실행부터 종료때까지 유저의 정보를 저장해두는 오브젝트
// 자바의 static 개념으로 보면 됨
object userInfo {
    var id : String = ""
    var pw : String = ""
    var pNum : String = ""
    var role : String = ""
    var ctgrForSeller : String = ""
    var timeOpen = ""
    var timeClose = ""
}
// 오늘을 위한 데이터
// 소비자 -> 장바구니
object instantData {
    var productList : ArrayList<productElement> = ArrayList()

}

object backPressedTime {
    var flag : Long = 0
}

object currentCondition {
    // 시장
    var marketTitle : String = ""
    // 완제품 or 재료
    var ctgr01 : String = ""
    // 육류, 해산물, 등등
    var ctgr02 : String = ""
}

object currentProductElement {
    var currentProductElement = productElement()
}
