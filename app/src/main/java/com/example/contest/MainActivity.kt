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
            val SignUp_user=Intent(this,SignUp::class.java)
            startActivity(SignUp_user)
        }
        SignUpButton_seller.setOnClickListener{
            val SignUp_seller=Intent(this,SignUp_seller::class.java)
            startActivity(SignUp_seller)
        }


    }

    private fun login(id: String, pw: String) {
        userInfo.id = id
        userInfo.pw = pw
        // 만약 계정정보가 판매자다 -> 판매자 UI 불러오기
        // 소비자다 -> 소비자 UI 불러오기
        ///////////////
        // //
        // //
        // //
        // //
        // //
        // DB 데이터 불러오기
        //
        //
        //
        //
        //
        //
        //
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
        for (i in 0 until 2) {
            for ( j in 0 until 2) {
                var dummy = productElement("Test_$i$j")
                sampledb.productList.add(dummy)
            }
        }
    }

    // 날짜에 해당하는 상품들을 넣는 함수
    /// #주의: 이 함수 쓰기 전에 그 유저에 해당하는 상품만 걸러야됨
    fun putProductList(date : String, productList : ArrayList<productElement>) {
        sampledb.productMap[date] = productList
        sampledb.productDates.add(date)
    }

}

// 앱 실행부터 종료때까지 유저의 정보를 저장해두는 오브젝트
// 자바의 static 개념으로 보면 됨
object userInfo {
    var id : String = ""
    var pw : String = ""
}

// 휘발성 데이터
// 로그인 할때부터 로그아웃 할때까지만 있는 데이터
/// 1. 판매자가 로그인
//// 이중어레이 {날짜: ArrayList<productElement>, 날짜: ArrayList<productElement>, ...}
//// a. 맵 변수 만들기
//// b. 날짜 별 엘레먼트 만들기 -> {날짜: ArrayList<productElement>}
//// c. 맵 변수에 넣어주기
object sampledb {
    var productMap = mutableMapOf<String, Any>()
    // productDates: 날짜 리스트 = productMap의 key value 들
    var productDates :ArrayList<String> = ArrayList()
    // ProductList: 오늘의 상품들
    var productList : ArrayList<productElement> = ArrayList()
}
