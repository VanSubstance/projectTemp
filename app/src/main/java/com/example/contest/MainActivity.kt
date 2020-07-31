package com.example.contest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton.setOnClickListener {
            login(userID.text.toString(), userPW.text.toString())
        }
        SignUpButton.setOnClickListener{
            val SignUp_user=Intent(this,signup_sellect::class.java)
            startActivity(SignUp_user)
        }

    }

    private fun login(id: String, pw: String) {
        userInfo.id = id
        userInfo.pw = pw
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()

        val data = database.getReference("userDB")
        val sellerUI = Intent(this, sellerUIMain :: class.java)
        val buyerUI = Intent(this, buyerUIMain :: class.java)
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (client in p0.children) {
                    if (userInfo.id.equals(client.child("id").value) && userInfo.pw.equals(client.child("pw").value)) {
                        userInfo.role = client.child("role").value as String
                        userInfo.pNum = client.child("pNum").value as String
                        if (userInfo.role.equals("seller")) {
                            startActivity(sellerUI)
                        } else {
                            startActivity(buyerUI)
                        }
                    }
                }
                Toast.makeText(this@MainActivity, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
            }
        })
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
    var pNum : String = ""
    var role : String = ""
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
