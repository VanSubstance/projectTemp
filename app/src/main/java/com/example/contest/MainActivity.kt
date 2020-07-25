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
            // 예시 더미 데이터 생성
            createDummy()
            login(userID.text.toString(), userPW.text.toString())
        }
        SignUpButton.setOnClickListener{
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        SignUpButton_seller.setOnClickListener{
            val intent=Intent(this,signUp_seller::class.java)
            startActivity(intent)
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
                var dummy = productElement("조건부 데이터_$i$j")
                conditionData.productList.add(dummy)
                var dummy2 = productElement("오늘 데이터_$i$j")
                instantData.productList.add(dummy2)
            }
        }
    }

    // 날짜에 해당하는 상품들을 넣는 함수
    /// #주의: 이 함수 쓰기 전에 그 유저에 해당하는 상품만 걸러야됨
    fun putProductList(date : String, productList : ArrayList<productElement>) {
        conditionData.productMap[date] = productList
        conditionData.productDates.add(date)
    }

}

// 앱 실행부터 종료때까지 유저의 정보를 저장해두는 오브젝트
// 자바의 static 개념으로 보면 됨
object userInfo {
    var id : String = ""
    var pw : String = ""
}

// 휘발성 데이터
// 조건에 맞는 데이터들을 불러와서 보관하는 컨테이너 개념
object conditionData {
    var productMap = mutableMapOf<String, Any>()
    // productDates: 날짜 리스트 = productMap의 key value 들
    var productDates :ArrayList<String> = ArrayList()
    // ProductList: 오늘의 상품들
    var productList : ArrayList<productElement> = ArrayList()
}

// 오늘을 위한 데이터
// 판매자 -> 오늘자 등록한 상품들
// 소비자 -> 장바구니
object instantData {
    var productList : ArrayList<productElement> = ArrayList()

}
