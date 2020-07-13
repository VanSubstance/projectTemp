package com.example.contest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton.setOnClickListener {
            login(userID.text.toString(), userPW.text.toString())
        }

    }

    private fun login(id: String, pw: String) {
        userInfo.id = id
        userInfo.pw = pw
        // 만약 계정정보가 판매자다 -> 판매자 UI 불러오기
        // 소비자다 -> 소비자 UI 불러오기
        // DB 데이터 불러오기
        if (id == "a" && pw == "a") {
            val userUI = Intent(this, sellerUIMain :: class.java)
            startActivity(userUI)
        }
        else {
            val userUI = Intent(this, buyerUIMain :: class.java)
            startActivity(userUI)
        }
    }

}

// 앱 실행부터 종료때까지 유저의 정보를 저장해두는 오브젝트
// 자바의 static 개념으로 보면 됨
object userInfo {
    var id : String = ""
    var pw : String = ""
}
