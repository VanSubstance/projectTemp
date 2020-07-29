package com.example.contest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ValueEventListener as ValueEventListener
import com.google.firebase.database.FirebaseDatabase
import android.util.Log
import kotlin.collections.listOf


class MainActivity : AppCompatActivity() {


    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val TAG: String = "SignIn"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 예시 더미 데이터 생성
        createDummy()
        //계정 로그인
        auth =FirebaseAuth.getInstance()


        // 파이어베이스에서 데이터 읽기
        loginButton.setOnClickListener {
            login(userEmail.text.toString(), userPW.text.toString(),userPN.text.toString())
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

    private fun login(email: String, pw: String,pn:String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("users")
        userInfo.email = email
        userInfo.pw = pw
        userInfo.pnum=pn
        //로그인 입력값 받는거

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
    var email : String = ""
    var pw : String = ""
    var pnum : String=""
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
