package com.example.contest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 예시 더미 데이터 생성
        createDummy()

        loginButton.setOnClickListener {
            login(userID.text.toString(), userPW.text.toString())
        }
        SignUpButton.setOnClickListener{
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
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

    fun createDummy() {
        for (i in 0 until 4) {
            for ( j in 0 until 4) {
                var dummy = productElement("Test_$i$j")
                sampledb.productList.add(dummy)
            }
        }
    }

}

// 앱 실행부터 종료때까지 유저의 정보를 저장해두는 오브젝트
// 자바의 static 개념으로 보면 됨
object userInfo {
    var id : String = ""
    var pw : String = ""
}

object sampledb {
    var productList : ArrayList<productElement> = ArrayList()

}
